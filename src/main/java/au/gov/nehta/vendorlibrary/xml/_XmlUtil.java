/*
 * Copyright 2011 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'LICENSE.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.xml;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.xml.soap.SOAPMessage;

/**
 * A helper class to obtain easy-to-read XML from SOAPMessage objects.
 */
public final class _XmlUtil {
    /**
     * Logger instance for Logging messages to log outputstream
     */
    private static final Logger LOG = Logger.getLogger(XmlUtil.class.getName());

    private _XmlUtil() {
    }

    /**
     * Returns an easy to read String representation of the SOAP XML Message.
     *
     * @param message the SOAP XML Message (Mandatory)
     * @return an easy to read XML document as a String
     */
    public static String serialiseSoapXml(final SOAPMessage message) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String soap = "";

        try {
            message.writeTo(baos);
            soap = baos.toString();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Could not parse the soap message into a UTF-8 string ", e);
        }

        return soap;
    }
}
