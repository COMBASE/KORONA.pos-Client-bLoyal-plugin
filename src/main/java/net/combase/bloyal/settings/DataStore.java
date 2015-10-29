/**
 * 
 */
package net.combase.bloyal.settings;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author "Till Freier"
 *
 */
@XmlRootElement
public class DataStore {
	private Settings settings = new Settings();

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	
}
