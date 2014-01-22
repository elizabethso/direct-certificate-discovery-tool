package gov.hhs.onc.dcdt.crypto.keys.impl;

import gov.hhs.onc.dcdt.crypto.impl.AbstractCryptographyDescriptor;
import gov.hhs.onc.dcdt.crypto.keys.KeyAlgorithm;
import gov.hhs.onc.dcdt.crypto.keys.KeyInfo;
import gov.hhs.onc.dcdt.crypto.utils.CryptographyUtils;
import gov.hhs.onc.dcdt.utils.ToolClassUtils;
import gov.hhs.onc.dcdt.utils.ToolNumberUtils;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import javax.annotation.Nullable;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyInfoImpl extends AbstractCryptographyDescriptor implements KeyInfo {
    private KeyPair keyPair;

    public KeyInfoImpl() {
        this(null);
    }

    public KeyInfoImpl(@Nullable PublicKey publicKey, @Nullable PrivateKey privateKey) {
        this(new KeyPair(publicKey, privateKey));
    }

    public KeyInfoImpl(@Nullable KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public boolean hasAuthorityKeyId() {
        return this.getAuthorityKeyId() != null;
    }

    @Nullable
    @Override
    public AuthorityKeyIdentifier getAuthorityKeyId() {
        return this.hasSubjectPublicKeyInfo() ? new AuthorityKeyIdentifier(this.getSubjectPublicKeyInfo()) : null;
    }

    @Override
    public boolean hasKeyPair() {
        return this.keyPair != null;
    }

    @Nullable
    @Override
    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    @Override
    public void setKeyPair(@Nullable KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public boolean hasPublicKey() {
        return this.getPublicKey() != null;
    }

    @Nullable
    @Override
    public PublicKey getPublicKey() {
        return this.hasKeyPair() ? this.keyPair.getPublic() : null;
    }

    @Override
    public void setPublicKey(@Nullable PublicKey publicKey) {
        this.keyPair = new KeyPair(publicKey, this.getPrivateKey());
    }

    @Override
    public boolean hasPrivateKey() {
        return this.getPrivateKey() != null;
    }

    @Nullable
    @Override
    public PrivateKey getPrivateKey() {
        return this.hasKeyPair() ? this.keyPair.getPrivate() : null;
    }

    @Override
    public void setPrivateKey(@Nullable PrivateKey privateKey) {
        this.keyPair = new KeyPair(this.getPublicKey(), privateKey);
    }

    @Override
    public boolean hasPrivateKeyInfo() {
        return this.getPrivateKeyInfo() != null;
    }

    @Nullable
    @Override
    public PrivateKeyInfo getPrivateKeyInfo() {
        PrivateKey privateKey = this.getPrivateKey();

        return (privateKey != null) ? PrivateKeyInfo.getInstance(privateKey.getEncoded()) : null;
    }

    @Override
    public boolean hasKeyAlgorithm() {
        return this.getKeyAlgorithm() != null;
    }

    @Nullable
    @Override
    public KeyAlgorithm getKeyAlgorithm() {
        Key availKey = this.getAvailableKey();

        return (availKey != null) ? CryptographyUtils.findId(KeyAlgorithm.class, availKey.getAlgorithm()) : null;
    }

    @Override
    public boolean hasKeySize() {
        return ToolNumberUtils.isPositive(this.getKeySize());
    }

    @Nullable
    @Override
    public Integer getKeySize() {
        Key availKey = this.getAvailableKey();

        return ((availKey != null) && ToolClassUtils.isAssignable(RSAKey.class, availKey.getClass())) ? ((RSAKey) availKey).getModulus().bitLength() : null;
    }

    @Override
    public boolean hasSubjectKeyId() {
        return this.getSubjectKeyId() != null;
    }

    @Nullable
    @Override
    public SubjectKeyIdentifier getSubjectKeyId() {
        return this.hasPublicKey() ? new SubjectKeyIdentifier(DigestUtils.getSha1Digest().digest(this.getPublicKey().getEncoded())) : null;
    }

    @Override
    public boolean hasSubjectPublicKeyInfo() {
        return this.getSubjectPublicKeyInfo() != null;
    }

    @Nullable
    @Override
    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        PublicKey publicKey = this.getPublicKey();

        return (publicKey != null) ? SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()) : null;
    }

    @Nullable
    protected Key getAvailableKey() {
        return ObjectUtils.defaultIfNull(this.getPublicKey(), this.getPrivateKey());
    }
}
