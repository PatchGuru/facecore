/*
 * This file is part of Facecore, licensed under the ISC License.
 *
 * Copyright (c) 2014 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package com.tealcube.minecraft.bukkit.facecore;

import com.tealcube.minecraft.bukkit.config.SmartYamlConfiguration;
import com.tealcube.minecraft.bukkit.facecore.plugin.FacePlugin;
import com.tealcube.minecraft.bukkit.facecore.profile.PlayerJoinListener;
import org.bukkit.event.HandlerList;
import com.tealcube.minecraft.bukkit.facecore.profile.PlayerResolver;

import java.io.File;

public final class FacecorePlugin extends FacePlugin {

    private SmartYamlConfiguration playerDataYAML;
    private PlayerJoinListener playerJoinListener;

    @Override
    public void enable() {
        playerDataYAML = new SmartYamlConfiguration(new File(getDataFolder(), "playerData.yml"));
        PlayerResolver.getInstance().loadFrom(playerDataYAML);
        playerJoinListener = new PlayerJoinListener(this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
    }

    @Override
    public void disable() {
        PlayerResolver.getInstance().saveTo(playerDataYAML);
        HandlerList.unregisterAll(this);
        playerJoinListener = null;
        playerDataYAML = null;
    }

}
