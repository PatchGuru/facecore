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
package com.tealcube.minecraft.bukkit.facecore.profile;

import com.tealcube.minecraft.bukkit.config.SmartYamlConfiguration;
import com.tealcube.minecraft.bukkit.kern.shade.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerResolver {

    private static PlayerResolver instance;
    private final Map<String, Profile> profileMap = new ConcurrentHashMap<>();

    private PlayerResolver() {
        // do nothing
    }

    public List<Optional<Profile>> findAllProfiles(String name) {
        List<Optional<Profile>> optionals = new ArrayList<>();
        for (Profile profile : profileMap.values()) {
            if (profile != null && (profile.getName().equals(name) || profile.getLastKnownName().equals(name))) {
                optionals.add(Optional.of(profile));
            }
        }
        return optionals;
    }

    public List<Optional<Profile>> findAllProfiles(UUID uuid) {
        List<Optional<Profile>> optionals = new ArrayList<>();
        for (Profile profile : profileMap.values()) {
            if (profile != null && profile.getUuid().equals(uuid)) {
                optionals.add(Optional.of(profile));
            }
        }
        return optionals;
    }

    public Optional<Profile> findProfile(String name) {
        Optional<Profile> optional = Optional.absent();
        List<Optional<Profile>> profiles = findAllProfiles(name);
        if (profiles.size() > 0) {
            return profiles.get(0);
        }
        return optional;
    }

    public Optional<Profile> findProfile(UUID uuid) {
        Optional<Profile> optional = Optional.absent();
        List<Optional<Profile>> profiles = findAllProfiles(uuid);
        if (profiles.size() > 0) {
            return profiles.get(0);
        }
        return optional;
    }

    public void addProfile(Profile profile) {
        if (profile == null) {
            return;
        }
        profileMap.put(profile.getName().toLowerCase(), profile);
    }

    public void loadFrom(SmartYamlConfiguration config) {
        config.load();
        for (String s : config.getKeys(false)) {
            if (!config.isConfigurationSection(s)) {
                continue;
            }
            UUID uuid = UUID.fromString(s);
            String name = config.getString(s + ".name");
            String lastKnownName = config.getString(s + ".last-known-name");
            Profile profile = new Profile(uuid, name, lastKnownName);
            addProfile(profile);
        }
    }

    public void saveTo(SmartYamlConfiguration config) {
        for (Profile p : profileMap.values()) {
            config.set(p.getUuid().toString() + ".name", p.getName());
            config.set(p.getUuid().toString() + ".last-known-name", p.getLastKnownName());
        }
        config.save();
    }

    public static PlayerResolver getInstance() {
        if (instance == null) {
            instance = new PlayerResolver();
        }
        return instance;
    }

}
