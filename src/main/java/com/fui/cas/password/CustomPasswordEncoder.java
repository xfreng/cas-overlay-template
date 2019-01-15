package com.fui.cas.password;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.Charset;

/**
 * @Author sf.xiong on 2018-07-12.
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomPasswordEncoder.class);
    private String encodingAlgorithm = "MD5";
    private String characterEncoding = "UTF-8";

    @Override
    public String encode(CharSequence password) {
        if (password == null) {
            return null;
        } else if (StringUtils.isBlank(this.encodingAlgorithm)) {
            LOGGER.warn("No encoding algorithm is defined. Password cannot be encoded; Return null");
            return null;
        } else {
            String encodingCharToUse = StringUtils.isNotBlank(this.characterEncoding) ? this.characterEncoding : Charset.defaultCharset().name();
            LOGGER.debug("Using [{}] as the character encoding algorithm to update the digest", encodingCharToUse);
            try {
                byte[] pwdBytes = password.toString().getBytes(encodingCharToUse);
                String encoded = Hex.encodeHexString(DigestUtils.getDigest(this.encodingAlgorithm).digest(pwdBytes));
                LOGGER.debug("Encoded password via algorithm [{}] and character-encoding [{}] is [{}]", this.encodingAlgorithm, encodingCharToUse, encoded);
                return encoded;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRawPassword = StringUtils.isNotBlank(rawPassword) ? this.encode(rawPassword.toString()).toUpperCase() : null;
        boolean matched = StringUtils.equals(encodedRawPassword, encodedPassword);
        LOGGER.debug("Provided password does {} match the encoded password", BooleanUtils.toString(matched, "", " not "));
        return matched;
    }
}
