package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.range.Range;

/**
 * Constructs a {@link CaptureRequest} in a sane way.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CaptureRequestBuilder {

    final CameraDevice cameraDevice;
    final int requestTemplate;
    List<Surface> surfaces;
    Flash flash;
    FocusMode focus;
    Range<Integer> previewFpsRange;
    boolean shouldTriggerAutoFocus;
    boolean triggerPrecaptureExposure;
    boolean cancelPrecaptureExposure;
    boolean shouldSetExposureMode;
    Integer sensorSensitivity;
    Integer jpegQuality;
    int width;
    int height;
    boolean hasMeteringAreaSupport;

    private CaptureRequestBuilder(CameraDevice cameraDevice, @RequestTemplate int requestTemplate) {
        this.cameraDevice = cameraDevice;
        this.requestTemplate = requestTemplate;
    }

    static CaptureRequestBuilder create(CameraDevice cameraDevice,
                                        @RequestTemplate int requestTemplate) {

        return new CaptureRequestBuilder(cameraDevice, requestTemplate);
    }

    /**
     * @see CaptureRequest.Builder#addTarget(Surface)
     */
    CaptureRequestBuilder into(Surface surface) {
        this.surfaces = Collections.singletonList(surface);
        return this;
    }

    /**
     * @see CaptureRequest.Builder#addTarget(Surface)
     */
    CaptureRequestBuilder into(Surface... surfaces) {
        this.surfaces = Arrays.asList(surfaces);
        return this;
    }

    CaptureRequestBuilder flash(Flash flash) {
        this.flash = flash;
        return this;
    }

    CaptureRequestBuilder focus(FocusMode focus) {
        this.focus = focus;
        return this;
    }

    CaptureRequestBuilder previewFpsRange(Range<Integer> previewFpsRange) {
        this.previewFpsRange = previewFpsRange;
        return this;
    }

    CaptureRequestBuilder setExposureMode(boolean shouldSetExposureMode) {
        this.shouldSetExposureMode = shouldSetExposureMode;
        return this;
    }

    CaptureRequestBuilder triggerAutoFocus(boolean shouldTriggerAutoFocus) {
        this.shouldTriggerAutoFocus = shouldTriggerAutoFocus;
        return this;
    }

    CaptureRequestBuilder triggerPrecaptureExposure(boolean shouldTriggerPrecaptureExposure) {
        this.triggerPrecaptureExposure = shouldTriggerPrecaptureExposure;
        return this;
    }

    CaptureRequestBuilder cancelPrecaptureExposure(boolean cancelPrecaptureExposure) {
        this.cancelPrecaptureExposure = cancelPrecaptureExposure;
        return this;
    }

    CaptureRequestBuilder sensorSensitivity(Integer sensorSensitivity) {
        this.sensorSensitivity = sensorSensitivity;
        return this;
    }

    CaptureRequestBuilder jpegQuality(Integer jpegQuality) {
        this.jpegQuality = jpegQuality;
        return this;
    }

    CaptureRequestBuilder widthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    CaptureRequestBuilder supportMeteringArea(boolean hasMeteringAreaSupport){
        this.hasMeteringAreaSupport = hasMeteringAreaSupport;
        return this;
    }

    /**
     * Builds a {@link CaptureRequest} based on the builder parameters.
     *
     * @return The capture request.
     * @throws CameraAccessException If the camera device has been disconnected.
     */
    CaptureRequest build() throws CameraAccessException {
        validate();

        return Request.create(this);
    }

    private void validate() {
        if(width == 0 || height == 0){
            throw new IllegalStateException("width and height of sensor is mandatory.");
        }
        if (surfaces == null || surfaces.isEmpty()) {
            throw new IllegalStateException("Surface is mandatory.");
        }
        if (shouldSetExposureMode && flash == null) {
            throw new IllegalStateException(
                    "Requested to set the exposure mode but the flash mode has not been provided."
            );
        }

        if (triggerPrecaptureExposure && cancelPrecaptureExposure) {
            throw new IllegalStateException(
                    "Cannot perform both Trigger and Cancel precapture exposure actions in the same request.");
        }

    }
}
