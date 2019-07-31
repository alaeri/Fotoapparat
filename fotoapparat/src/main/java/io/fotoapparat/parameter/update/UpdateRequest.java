package io.fotoapparat.parameter.update;

import android.support.annotation.Nullable;

import java.util.Collection;

import io.fotoapparat.FotoapparatBuilder;
import io.fotoapparat.parameter.AntiBandingMode;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Request for updating some of the parameters of the {@link io.fotoapparat.Fotoapparat}.
 * <p>
 * Use {@link UpdateRequest#builder()} to create a new instance.
 * <p>
 * Fields with {@code null} values are ignored and not updated.
 */
public class UpdateRequest {

    /**
     * Selects flash mode from list of available modes.
     * <p>
     * {@code null} if no update is required.
     */
    @Nullable
    public final SelectorFunction<Collection<Flash>, Flash> flashSelector;

    /**
     * Selects anti banding mode from list of available modes.
     * <p>
     * {@code null} if no update is required.
     */
    @Nullable
    public final SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> antiBandingModeSelector;

    public final boolean centerExposure;

    /**
     * Selects focus mode from list of available modes.
     * <p>
     * {@code null} if no update is required.
     */
    @Nullable
    public final SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector;

    private UpdateRequest(Builder builder) {
        this.flashSelector = builder.flashSelector;
        this.antiBandingModeSelector = builder.antiBandingModeSelector;
        this.focusModeSelector = builder.focusModeSelector;
        this.centerExposure = builder.centerExposure;
    }

    /**
     * @return builder for {@link UpdateRequest}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link UpdateRequest}.
     */
    public static class Builder {

        SelectorFunction<Collection<Flash>, Flash> flashSelector = null;
        SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> antiBandingModeSelector = null;
        SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector = null;
        boolean centerExposure = false;

        /**
         * @param selector selects anti banding mode from list of available modes.
         */
        public Builder antiBandingMode(@Nullable SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> selector) {
            this.antiBandingModeSelector = selector;
            return this;
        }

        /**
         * @param selector selects focus mode from list of available modes.
         */
        public Builder focusMode(@Nullable SelectorFunction<Collection<FocusMode>, FocusMode> selector) {
            this.focusModeSelector = selector;
            return this;
        }

        /**
         * @param selector selects flash mode from list of available modes.
         */
        public Builder flash(@Nullable SelectorFunction<Collection<Flash>, Flash> selector) {
            this.flashSelector = selector;
            return this;
        }

        public Builder centerExposure(boolean b) {
            this.centerExposure = true;
            return this;
        }

        /**
         * @return a new instance of {@link UpdateRequest} which uses values from current builder.
         */
        public UpdateRequest build() {
            return new UpdateRequest(this);
        }


    }

}
