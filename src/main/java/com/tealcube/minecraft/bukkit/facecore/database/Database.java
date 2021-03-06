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
package com.tealcube.minecraft.bukkit.facecore.database;

import java.sql.Connection;

/**
 * An abstract base class for all database representations.
 */
public abstract class Database {

    /**
     * Initializes the database connection. Returns true if successful, false if not.
     * @return success
     */
    public abstract boolean initialize();

    /**
     * Shuts down the database connection. Returns true if successful, false if not.
     * @return success
     */
    public abstract boolean shutdown();

    /**
     * Returns true if the database uses a pool of {@link java.sql.Connection}s.
     * @return if database uses pool of Connections.
     */
    public abstract boolean isPool();

    /**
     * Returns a {@link java.sql.Connection} for use.
     *
     * If {@link Database#isPool()} is true, returns a new
     * {@link java.sql.Connection}, otherwise returns available {@link java.sql.Connection}.
     * @return Connection for use
     */
    public abstract Connection getConnection();

}
