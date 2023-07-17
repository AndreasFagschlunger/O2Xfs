package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

/**
 * @author sundar
 * @since 8.1.0
 */
public enum SIUEnhancedMicroPhoneControllerState implements XfsConstant
{
    /**
     *
     */
    NOT_AVAILABLE(0x0000L),

    /**
     * The Enhanced Microphone Controller is in manual mode and is in the public state (i.e.
     * the microphone in the fascia is active). Activating a Privacy Device (headset
     * connected/handset off-hook) will have no impact, i.e. input will remain through the
     * fascia microphone and any microphone associated with the Privacy Device will not be active.
     */
    PUBLICAUDIO_MANUAL(0x0001L),

    /**
     * The Enhanced Microphone Controller is in auto mode and is in the public state (i.e. the
     * microphone in the fascia is active). When a Privacy Device with a microphone is
     * activated, the device will go to the private state.
     */
    PUBLICAUDIO_AUTO(0x0002L),

    /**
     * The Enhanced Microphone Controller is in semi-auto mode and is in the public state (i.e.
     * the microphone in the fascia is active). When a Privacy Device with a microphone is
     * activated, the device will go to the private state.
     */
    PUBLICAUDIO_SEMI_AUTO(0x0004L),

    /**
     * The Enhanced Microphone Controller is in manual mode and is in the private state (i.e.
     * audio input will be via a microphone in the Privacy Device). In private mode, no audio
     * input is transmitted through the fascia microphone.
     */
    PRIVATEAUDIO_MANUAL(0x0008L),

    /**
     * The Enhanced Microphone Controller is in auto mode and is in the private state (i.e.
     * audio input will be via a microphone in the Privacy Device). In private mode, no audio
     * input is transmitted through the fascia microphone. When a Privacy Device with a
     * microphone is deactivated (headset disconnected/handset on-hook), the device
     * will go to the public state. Where there is more than one Privacy Device with a
     * microphone, the device will go to the public state only when all such Privacy Devices have been deactivated.
     */
    PRIVATEAUDIO_AUTO(0x0010L),

    /**
     * The Enhanced Microphone Controller is in semi-auto mode and is in the private state
     * (i.e. audio input will be via a microphone in the Privacy Device). In private mode, no
     * audio is transmitted through the fascia microphone. When a Privacy Device with a
     * microphone is deactivated, the device will remain in the private state
     */
    PRIVATEAUDIO_SEMI_AUTO(0x0020L);

    private final long value;

    private SIUEnhancedMicroPhoneControllerState(final long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }
}
