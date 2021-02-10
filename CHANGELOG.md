# Change Log/Revision History

= 1.1.1 =
=========
- Converted to Maven
- Changed dependencies to use Maven references

= 1.1.0 =
=======
- updated the supplied nehta-smi-xsp library to the 1.2.0 version which supports Java 1.7_21+
- deprecated use of verifySignature(Document,CertificateVerifier) in favour of verifySignature(Document,CertificateValidator)
- changed Logging handler implementation to avoid XML transformations.

= 1.0.4 =
=======
- modified WebServiceClient to add both com.sun.xml and com.sun.xml.internal binding for SSL factory