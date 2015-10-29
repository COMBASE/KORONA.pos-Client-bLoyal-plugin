/**
 * 
 */
package net.combase.bloyal.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * 
 * @author "Till Freier"
 *
 */
public final class DataStoreManager
{
	private static DataStoreManager instance = null;

	public static final DataStoreManager get()
	{
		if (instance == null)
			instance = new DataStoreManager();

		return instance;
	}

	private Settings settings;


	private DataStoreManager()
	{
		super();
	}

	private DataStore load() throws JAXBException, FileNotFoundException
	{
		File f = getStoreFile();
		
		if (!f.exists())
			return new DataStore();
		
		JAXBContext context = JAXBContext.newInstance(DataStore.class);
		Unmarshaller m = context.createUnmarshaller();
		return (DataStore)m.unmarshal(new FileReader(f));
	}

	private void write(DataStore store) throws JAXBException, IOException
	{
		File f = getStoreFile();
		
		JAXBContext context = JAXBContext.newInstance(DataStore.class);
		Marshaller m = context.createMarshaller();
		m.marshal(store, new FileWriter(f));
	}

	private File getStoreFile()
	{
		String home = System.getProperty("user.home");
		File f = new File(home, ".koronaBLoyalStore.xml");
		return f;
	}
	
	public Settings getSettings()
	{
		if (settings != null)
			return settings;
		try {
			return settings = load().getSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Settings();
	}
	
	public void writeSettings(Settings s)
	{
		try {
			DataStore load = load();
			load.setSettings(s);
			write(load);
			settings = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
