/**
 * 
 */
package net.combase.bloyal;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import javax.swing.JFrame;

import net.combase.bloyal.settings.DataStoreManager;
import net.combase.bloyal.settings.Settings;
import net.combase.bloyal.ui.BLoyalPluginConfigurationPanel;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author "Till Freier"
 *
 */
public class BLoyalPluginApplication
{
	private static String getVersion()
	{
		try
		{
			InputStream stream = BLoyalPluginApplication.class.getResourceAsStream("/META-INF/MANIFEST.MF");
			Manifest m = new Manifest(stream);
			return m.getMainAttributes().getValue("Project-Version");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "0";
	}

	public static void main(String[] args) throws Exception
	{
		final Settings settings = DataStoreManager.get().getSettings();
		BLoyalPluginConfigurationPanel configPanel = new BLoyalPluginConfigurationPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4396216797735388471L;

			@Override
			protected void onSaveStart()
			{
				writeSettings(settings);
				DataStoreManager.get().writeSettings(settings);
				updateUrls("http://localhost:" + settings.getPort());
				try
				{
					runWebserver(settings.getPort());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			@Override
			protected void onCancel()
			{
				System.exit(0);
			}
		};
		configPanel.readSettings(settings);

		JFrame f = new JFrame("KORONA BLoyal Plugin - " + getVersion());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(configPanel);
		f.setVisible(true);
		f.setSize(650, 550);


	}

	/**
	 * @throws IOException
	 * @throws Exception
	 * @throws InterruptedException
	 */
	private static void runWebserver(int port) throws IOException, Exception, InterruptedException
	{
		Server server = new Server(port);
		WebAppContext c = new WebAppContext();
		c.setServer(server);
		c.setClassLoader(new WebAppClassLoader(BLoyalPluginApplication.class.getClassLoader(), c));
		c.setDescriptor(BLoyalPluginApplication.class.getResource("web/WEB-INF/web.xml").toString());
		c.setResourceBase(BLoyalPluginApplication.class.getResource("web").toString());
		server.setHandler(c);
		server.start();
		// server.join();
	}
}