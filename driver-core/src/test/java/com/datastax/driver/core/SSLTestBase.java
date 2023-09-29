/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.driver.core;

import com.google.common.base.Optional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.SecureRandom;

import static com.datastax.driver.core.CCMBridge.DEFAULT_CLIENT_KEYSTORE_PASSWORD;
import static com.datastax.driver.core.CCMBridge.DEFAULT_CLIENT_TRUSTSTORE_PASSWORD;

public abstract class SSLTestBase {

    private final boolean requireClientAuth;

    protected CCMBridge ccm;

    public SSLTestBase(boolean requireClientAuth) {
        this.requireClientAuth = requireClientAuth;
    }

    @BeforeClass(groups = {"isolated", "short", "long"})
    public void beforeClass() {
        ccm = CCMBridge.builder("test")
                .withSSL(requireClientAuth)
                .build();
    }

    @AfterClass(groups = {"isolated", "short", "long"})
    public void afterClass() {
        ccm.remove();
    }

    /**
     * <p>
     * Attempts to connect to a cassandra cluster with the given SSLOptions and then closes the
     * created {@link Cluster} instance.
     * </p>
     *
     * @param sslOptions SSLOptions to use.
     * @throws Exception A {@link com.datastax.driver.core.exceptions.NoHostAvailableException} will be
     *                   raised here if connection cannot be established.
     */
    protected void connectWithSSLOptions(SSLOptions sslOptions) throws Exception {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint(CCMBridge.IP_PREFIX + '1')
                    .withSSL(sslOptions)
                    .build();

            cluster.connect();
        } finally {
            if (cluster != null)
                cluster.close();
        }
    }

    /**
     * <p>
     * Attempts to connect to a cassandra cluster with using {@link Cluster.Builder#withSSL} with no
     * provided {@link SSLOptions} and then closes the created {@link Cluster} instance.
     * </p>
     *
     * @throws Exception A {@link com.datastax.driver.core.exceptions.NoHostAvailableException} will be
     *                   raised here if connection cannot be established.
     */
    protected void connectWithSSL() throws Exception {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint(CCMBridge.IP_PREFIX + '1')
                    .withSSL()
                    .build();

            cluster.connect();
        } finally {
            if (cluster != null)
                cluster.close();
        }
    }

    /**
     * @param keyStorePath   Path to keystore, if absent is not used.
     * @param trustStorePath Path to truststore, if absent is not used.
     * @return {@link com.datastax.driver.core.SSLOptions} with the given keystore and truststore path's for
     * server certificate validation and client certificate authentication.
     */
    public SSLOptions getSSLOptions(Optional<String> keyStorePath, Optional<String> trustStorePath) throws Exception {

        TrustManagerFactory tmf = null;
        if (trustStorePath.isPresent()) {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(this.getClass().getResourceAsStream(trustStorePath.get()), DEFAULT_CLIENT_TRUSTSTORE_PASSWORD.toCharArray());

            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
        }

        KeyManagerFactory kmf = null;
        if (keyStorePath.isPresent()) {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(this.getClass().getResourceAsStream(keyStorePath.get()), DEFAULT_CLIENT_KEYSTORE_PASSWORD.toCharArray());

            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, DEFAULT_CLIENT_KEYSTORE_PASSWORD.toCharArray());
        }

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf != null ? kmf.getKeyManagers() : null, tmf != null ? tmf.getTrustManagers() : null, new SecureRandom());

        return new SSLOptions(sslContext, SSLOptions.DEFAULT_SSL_CIPHER_SUITES);
    }
}
