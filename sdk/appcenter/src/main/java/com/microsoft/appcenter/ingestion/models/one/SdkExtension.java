package com.microsoft.appcenter.ingestion.models.one;

import com.microsoft.appcenter.ingestion.models.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.UUID;

public class SdkExtension implements Model {

    /**
     * Library version property.
     */
    private static final String LIB_VER = "libVer";

    /**
     * Epoch property.
     */
    private static final String EPOCH = "epoch";

    /**
     * Sequence property.
     */
    private static final String SEQ = "seq";

    /**
     * InstallId property.
     */
    private static final String INSTALL_ID = "installId";

    /**
     * SDK version.
     */
    private String libVer;

    /**
     * Seed for each SDK initialization.
     */
    private String epoch;

    /**
     * ID incremented for each event.
     */
    private Long seq;

    /**
     * Install identifier.
     */
    private UUID installId;

    /**
     * Get SDK library version.
     *
     * @return SDK library version.
     */
    public String getLibVer() {
        return libVer;
    }

    /**
     * Set SDK library version.
     *
     * @param libVer SDK library version.
     */
    public void setLibVer(String libVer) {
        this.libVer = libVer;
    }

    /**
     * Get SDK epoch.
     *
     * @return SDK epoch.
     */
    public String getEpoch() {
        return epoch;
    }

    /**
     * Set SDK epoch.
     *
     * @param epoch SDK epoch.
     */
    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    /**
     * Get sequence number.
     *
     * @return sequence number.
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * Set sequence number.
     *
     * @param seq sequence number.
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
     * Get install identifier.
     *
     * @return install identifier.
     */
    public UUID getInstallId() {
        return installId;
    }

    /**
     * Set install identifier.
     *
     * @param installId install identifier.
     */
    public void setInstallId(UUID installId) {
        this.installId = installId;
    }

    @Override
    public void read(JSONObject object) throws JSONException {
        setLibVer(object.getString(LIB_VER));
        setEpoch(object.getString(EPOCH));
        setSeq(object.getLong(SEQ));
        setInstallId(UUID.fromString(object.getString(INSTALL_ID)));
    }

    @Override
    public void write(JSONStringer writer) throws JSONException {
        writer.key(LIB_VER).value(getLibVer());
        writer.key(EPOCH).value(getEpoch());
        writer.key(SEQ).value(getSeq());
        writer.key(INSTALL_ID).value(getInstallId());
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SdkExtension that = (SdkExtension) o;

        if (libVer != null ? !libVer.equals(that.libVer) : that.libVer != null) return false;
        if (epoch != null ? !epoch.equals(that.epoch) : that.epoch != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        return installId != null ? installId.equals(that.installId) : that.installId == null;
    }

    @Override
    public int hashCode() {
        int result = libVer != null ? libVer.hashCode() : 0;
        result = 31 * result + (epoch != null ? epoch.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (installId != null ? installId.hashCode() : 0);
        return result;
    }
}
