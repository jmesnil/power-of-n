/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2020, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package net.jmesnil.demo;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ContainerResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class PowerOfN_IT {

    @ContainerResource
    private ManagementClient managementClient;

    protected URI getHTTPEndpoint() {
        return managementClient.getWebUri();
    }

    @Test
    public void testZero() {

        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(getHTTPEndpoint())
                    .path("/")
                    .queryParam("value", 0)
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals(0, (long)response.readEntity(Long.class));

        } finally {
            client.close();
        }
    }

    @Test
    public void testPositiveValue() {

        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(getHTTPEndpoint())
                    .path("/")
                    .queryParam("value", 3)
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("9", response.readEntity(String.class));

        } finally {
            client.close();
        }
    }

    @Test
    public void testNonLongValues() {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(getHTTPEndpoint())
                    .path("/")
                    .queryParam("value", "this is not a long")
                    .request()
                    .get();
            assertEquals(404, response.getStatus());

        } finally {
            client.close();
        }
    }
}
