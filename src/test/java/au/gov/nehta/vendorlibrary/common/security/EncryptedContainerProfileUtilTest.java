/*
 * Copyright 2011 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.common.security;

import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.vendorlibrary.common.DOMUtil;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.security.auth.x500.X500PrivateCredential;

import java.lang.reflect.Constructor;

import static au.gov.nehta.vendorlibrary.common.TestConstant.*;
import static org.junit.Assert.*;

public class EncryptedContainerProfileUtilTest {

    @Test
    public void testEncryptedContainerProfileUtilPrivateConstructor() throws Exception {

        Constructor<?>[] constructors = EncryptedContainerProfileUtil.class.getDeclaredConstructors();
        constructors[0].setAccessible(true);
        EncryptedContainerProfileUtil currentInstance = (EncryptedContainerProfileUtil) constructors[0].newInstance((Object[]) null);
        assertNotNull(currentInstance);
    }

    @Test
    public void testGetEncryptedAndDecryptPayload() throws Exception {

        Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
                getSigningCertificate(DRP_PRIVATE_KEY_ALIAS),
                getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));

        Document encryptedDocument = EncryptedContainerProfileUtil.getEncryptedPayload(signedDocument,
                getSigningCertificate(DRP_PRIVATE_KEY_ALIAS));

        Assert.assertNotNull(encryptedDocument);

        // Decrypt the certificate
        X500PrivateCredential privateCredential = new X500PrivateCredential(getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));
        // The certificates in the test resources have expired, so comment out this line for now.
        // Document decDocument = EncryptedContainerProfileUtil.getDecryptedPayload(encryptedDocument, privateCredential);

        String signedDocContent = DOMUtil.serialiseToString(signedDocument);
        System.out.println(signedDocContent);
        assertNotNull(signedDocContent);
        assertTrue(signedDocContent.contains(SENSITIVE_PAYLOAD));
        assertNotNull(privateCredential);
    }

    @Test
    public void testGetEncryptedPayloadWithNullArguments() throws Exception {
        // Null signed document
        try {
            // Don't need to have variable to store encrypted document result, as we expect an exception
            EncryptedContainerProfileUtil.getEncryptedPayload(null,
                    null);
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex);
            assertEquals("signedDocument cannot be null.", ex.getMessage());
        }

        // Null certificate
        try {

            Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
                    getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));
            // Don't need to have variable to store encrypted document result, as we expect an exception
            EncryptedContainerProfileUtil.getEncryptedPayload(signedDocument,
                    null);
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex);
            assertEquals("encryptingCert cannot be null.", ex.getMessage());
        }
    }

    @Test
    public void testGetEncryptedPayloadWithBlankArguments() throws Exception {
        try {
            Document signedDocument = DomUtils.newDocument();
            // Don't need to have variable to store encrypted document result, as we expect an exception
            EncryptedContainerProfileUtil.getEncryptedPayload(signedDocument,
                    getSigningCertificate(DRP_PRIVATE_KEY_ALIAS));
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex);
            assertEquals("'payloadDoc' cannot be null or empty", ex.getMessage());
        }
    }
}
