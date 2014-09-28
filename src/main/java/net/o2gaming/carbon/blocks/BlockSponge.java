package net.o2gaming.carbon.blocks;

import net.minecraft.server.v1_7_R4.CreativeModeTab;

/**
 *
 * @author Navid
 */
public class BlockSponge extends net.minecraft.server.v1_7_R4.BlockSponge {

    public static final String[] names = {"dry", "wet"};
    
    public BlockSponge() {
        a(CreativeModeTab.b);
        c(0.6f);
        b(3);
    }

    @Override
    public int getDropData(int i) {
        return i;
    }
    
}
