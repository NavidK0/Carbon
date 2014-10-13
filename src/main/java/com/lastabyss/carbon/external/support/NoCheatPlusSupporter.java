package com.lastabyss.carbon.external.support;

import com.lastabyss.carbon.Carbon;
import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.NoCheatPlus;
import fr.neatmonster.nocheatplus.components.NoCheatPlusAPI;

/**
 *
 * @author Navid
 */
public class NoCheatPlusSupporter {

    Carbon plugin;
    NoCheatPlusAPI ncp = NCPAPIProvider.getNoCheatPlusAPI();

    public NoCheatPlusSupporter(Carbon plugin, NoCheatPlus ncp) {
        this.plugin = plugin;
        this.ncp = ncp;
    }

    
    
    public void hook() {
        
    }

    
}
