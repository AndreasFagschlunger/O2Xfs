package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

/**
 * @author sundar
 * @since 8.1.0
 */
public enum SIUFasciaMicroPhoneSensorState implements XfsConstant
{
    /**
     * The status is not available.
     */
    NOT_AVAILABLE(0x0000L),

    /**
     * The Fascia Microphone is turned off.
     */
    OFF(0x0001L),

    /**
     * The Fascia Microphone is turned on.
     */
    ON(0x0002L);

    private final long value;

    private SIUFasciaMicroPhoneSensorState(final long value) {
        this.value = value;
    }

    @Override
    public long getValue()
    {
        return value;
    }
}
