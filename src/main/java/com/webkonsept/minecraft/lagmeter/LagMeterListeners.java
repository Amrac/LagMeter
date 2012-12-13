package main.java.com.webkonsept.minecraft.lagmeter;

import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class LagMeterListeners implements Listener {

	public LagMeter plugin;
	
	public LagMeterListeners(LagMeter plugin) {
           	this.plugin = plugin;
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
     
    /*
     * Called when fluids flow.
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        if (plugin.fluids) {
            event.setCancelled(true);
            return;
        }  
    }
    
    /*
     * Called when a block gets ignited.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (plugin.eventBlockFire) {
            event.setCancelled(true);
            return;
        }
    }
    
    
    /*
     * Called when a block is destroyed from burning.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        if (plugin.eventBlockBurn) {
            event.setCancelled(true);
            return;
        }
    }
    
    /*
     * Called when block physics occurs.
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
       if (plugin.eventBlockPhysics) {
            event.setCancelled(true);
            return;
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (plugin.eventLeavesDecay) {
            event.setCancelled(true);
            return;
        }
    }
    
    /*
     * Called when a block is formed based on world conditions.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent event) {
        if (plugin.eventBlockForm) {
            event.setCancelled(true);
            return;
        }
    }
    
    /*
     * Called when a block spreads based on world conditions.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent event) {
        if (plugin.eventBlockSpread) {
            event.setCancelled(true);
            return;
        }
    }
    
    /*
     * Called on entity explode.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (plugin.eventEntityExplode) {
        	Entity ent = event.getEntity();
            ent.remove();
            event.setCancelled(true);
            return;
        }
    }
    
    /*
     * Called on explosion prime
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (plugin.eventExplosionPrime) {
        	Entity ent = event.getEntity();
            ent.remove();
            event.setCancelled(true);
            return;
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (plugin.eventCreatureSpawn) {
            event.setCancelled(true);
            return;
        }
    }
    
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (plugin.removeEntitiesOnChunkLoad) {
            int removed = 0;

            for (Entity entity : event.getChunk().getEntities()) {
                if (isIntensiveEntity(entity)) {
                    entity.remove();
                    removed++;
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (plugin.removeEntitiesOnPlayerJoin) {
            for (Entity entity : world.getEntities()) {
                if (isIntensiveEntity(entity)) {
                    entity.remove(); 
                }
            }
        }
    }
    
    public boolean isIntensiveEntity(Entity entity) {
    	if(plugin.removeEntitiesAnimal && entity instanceof Animals)
    		return true;
    	
    	if(plugin.removeEntitiesNPC && entity instanceof NPC)
    		return true;
    	
    	if(plugin.removeEntitiesNPC && entity instanceof NPC)
    		return true;
    		
    		
        return entity instanceof Item
                || entity instanceof TNTPrimed
                || entity instanceof ExperienceOrb
                || entity instanceof FallingBlock
                || (entity instanceof LivingEntity
                    && !(entity instanceof Animals)
                    && !(entity instanceof NPC)
                    && !(entity instanceof Player));
    }
}