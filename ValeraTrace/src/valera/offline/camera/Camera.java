package valera.offline.camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Camera {

	private static final int CAMERA_MSG_ERROR            = 0x001;
    private static final int CAMERA_MSG_SHUTTER          = 0x002;
    private static final int CAMERA_MSG_FOCUS            = 0x004;
    private static final int CAMERA_MSG_ZOOM             = 0x008;
    private static final int CAMERA_MSG_PREVIEW_FRAME    = 0x010;
    private static final int CAMERA_MSG_VIDEO_FRAME      = 0x020;
    private static final int CAMERA_MSG_POSTVIEW_FRAME   = 0x040;
    private static final int CAMERA_MSG_RAW_IMAGE        = 0x080;
    private static final int CAMERA_MSG_COMPRESSED_IMAGE = 0x100;
    private static final int CAMERA_MSG_RAW_IMAGE_NOTIFY = 0x200;
    private static final int CAMERA_MSG_PREVIEW_METADATA = 0x400;
    private static final int CAMERA_MSG_FOCUS_MOVE       = 0x800;

    
    public static String cameraMsgToString(int msg) {
    	switch (msg) {
    		case CAMERA_MSG_ERROR:
    			return "camera_error";
    		case CAMERA_MSG_SHUTTER:
    			return "camera_shutter";
    		case CAMERA_MSG_FOCUS:
    			return "camera_focus";
    		case CAMERA_MSG_ZOOM:
    			return "camera_zoom";
    		case CAMERA_MSG_PREVIEW_FRAME:
    			return "camera_priview_frame";
    		case CAMERA_MSG_VIDEO_FRAME:
    			return "camera_video_frame";
    		case CAMERA_MSG_POSTVIEW_FRAME:
    			return "camera_postview_frame";
    		case CAMERA_MSG_RAW_IMAGE:
    			return "camrea_raw_image";
    		case CAMERA_MSG_COMPRESSED_IMAGE:
    			return "camera_compressed_image";
    		case CAMERA_MSG_RAW_IMAGE_NOTIFY:
    			return "camera_image_notify";
    		case CAMERA_MSG_PREVIEW_METADATA:
    			return "camera_preview_metadata";
    		case CAMERA_MSG_FOCUS_MOVE:
    			return "camera_focus_move";
    	}
    	return "N/A";
    }
    
    
    public class Size {
        /**
         * Sets the dimensions for pictures.
         *
         * @param w the photo width (pixels)
         * @param h the photo height (pixels)
         */
        public Size(int w, int h) {
            width = w;
            height = h;
        }
        /**
         * Compares {@code obj} to this size.
         *
         * @param obj the object to compare this size with.
         * @return {@code true} if the width and height of {@code obj} is the
         *         same as those of this size. {@code false} otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Size)) {
                return false;
            }
            Size s = (Size) obj;
            return width == s.width && height == s.height;
        }
        @Override
        public int hashCode() {
            return width * 32713 + height;
        }
        /** width of the picture */
        public int width;
        /** height of the picture */
        public int height;
    };
    
    public static class Area {
        /**
         * Create an area with specified rectangle and weight.
         *
         * @param rect the bounds of the area.
         * @param weight the weight of the area.
         */
        public Area(Rect rect, int weight) {
            this.rect = rect;
            this.weight = weight;
        }
        /**
         * Compares {@code obj} to this area.
         *
         * @param obj the object to compare this area with.
         * @return {@code true} if the rectangle and weight of {@code obj} is
         *         the same as those of this area. {@code false} otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Area)) {
                return false;
            }
            Area a = (Area) obj;
            if (rect == null) {
                if (a.rect != null) return false;
            } else {
                if (!rect.equals(a.rect)) return false;
            }
            return weight == a.weight;
        }

        /**
         * Bounds of the area. (-1000, -1000) represents the top-left of the
         * camera field of view, and (1000, 1000) represents the bottom-right of
         * the field of view. Setting bounds outside that range is not
         * allowed. Bounds with zero or negative width or height are not
         * allowed.
         *
         * @see Parameters#getFocusAreas()
         * @see Parameters#getMeteringAreas()
         */
        public Rect rect;

        /**
         * Weight of the area. The weight must range from 1 to 1000, and
         * represents a weight for every pixel in the area. This means that a
         * large metering area with the same weight as a smaller area will have
         * more effect in the metering result.  Metering areas can overlap and
         * the driver will add the weights in the overlap region.
         *
         * @see Parameters#getFocusAreas()
         * @see Parameters#getMeteringAreas()
         */
        public int weight;
    }
    
    public class Parameters {
        // Parameter keys to communicate with the camera driver.
        private static final String KEY_PREVIEW_SIZE = "preview-size";
        private static final String KEY_PREVIEW_FORMAT = "preview-format";
        private static final String KEY_PREVIEW_FRAME_RATE = "preview-frame-rate";
        private static final String KEY_PREVIEW_FPS_RANGE = "preview-fps-range";
        private static final String KEY_PICTURE_SIZE = "picture-size";
        private static final String KEY_PICTURE_FORMAT = "picture-format";
        private static final String KEY_JPEG_THUMBNAIL_SIZE = "jpeg-thumbnail-size";
        private static final String KEY_JPEG_THUMBNAIL_WIDTH = "jpeg-thumbnail-width";
        private static final String KEY_JPEG_THUMBNAIL_HEIGHT = "jpeg-thumbnail-height";
        private static final String KEY_JPEG_THUMBNAIL_QUALITY = "jpeg-thumbnail-quality";
        private static final String KEY_JPEG_QUALITY = "jpeg-quality";
        private static final String KEY_ROTATION = "rotation";
        private static final String KEY_GPS_LATITUDE = "gps-latitude";
        private static final String KEY_GPS_LONGITUDE = "gps-longitude";
        private static final String KEY_GPS_ALTITUDE = "gps-altitude";
        private static final String KEY_GPS_TIMESTAMP = "gps-timestamp";
        private static final String KEY_GPS_PROCESSING_METHOD = "gps-processing-method";
        private static final String KEY_WHITE_BALANCE = "whitebalance";
        private static final String KEY_EFFECT = "effect";
        private static final String KEY_ANTIBANDING = "antibanding";
        private static final String KEY_SCENE_MODE = "scene-mode";
        private static final String KEY_FLASH_MODE = "flash-mode";
        private static final String KEY_FOCUS_MODE = "focus-mode";
        private static final String KEY_FOCUS_AREAS = "focus-areas";
        private static final String KEY_MAX_NUM_FOCUS_AREAS = "max-num-focus-areas";
        private static final String KEY_FOCAL_LENGTH = "focal-length";
        private static final String KEY_HORIZONTAL_VIEW_ANGLE = "horizontal-view-angle";
        private static final String KEY_VERTICAL_VIEW_ANGLE = "vertical-view-angle";
        private static final String KEY_EXPOSURE_COMPENSATION = "exposure-compensation";
        private static final String KEY_MAX_EXPOSURE_COMPENSATION = "max-exposure-compensation";
        private static final String KEY_MIN_EXPOSURE_COMPENSATION = "min-exposure-compensation";
        private static final String KEY_EXPOSURE_COMPENSATION_STEP = "exposure-compensation-step";
        private static final String KEY_AUTO_EXPOSURE_LOCK = "auto-exposure-lock";
        private static final String KEY_AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK = "auto-whitebalance-lock";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
        private static final String KEY_METERING_AREAS = "metering-areas";
        private static final String KEY_MAX_NUM_METERING_AREAS = "max-num-metering-areas";
        private static final String KEY_ZOOM = "zoom";
        private static final String KEY_MAX_ZOOM = "max-zoom";
        private static final String KEY_ZOOM_RATIOS = "zoom-ratios";
        private static final String KEY_ZOOM_SUPPORTED = "zoom-supported";
        private static final String KEY_SMOOTH_ZOOM_SUPPORTED = "smooth-zoom-supported";
        private static final String KEY_FOCUS_DISTANCES = "focus-distances";
        private static final String KEY_VIDEO_SIZE = "video-size";
        private static final String KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO =
                                            "preferred-preview-size-for-video";
        private static final String KEY_MAX_NUM_DETECTED_FACES_HW = "max-num-detected-faces-hw";
        private static final String KEY_MAX_NUM_DETECTED_FACES_SW = "max-num-detected-faces-sw";
        private static final String KEY_RECORDING_HINT = "recording-hint";
        private static final String KEY_VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
        private static final String KEY_VIDEO_STABILIZATION = "video-stabilization";
        private static final String KEY_VIDEO_STABILIZATION_SUPPORTED = "video-stabilization-supported";

        // Parameter key suffix for supported values.
        private static final String SUPPORTED_VALUES_SUFFIX = "-values";

        private static final String TRUE = "true";
        private static final String FALSE = "false";

        // Values for white balance settings.
        public static final String WHITE_BALANCE_AUTO = "auto";
        public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
        public static final String WHITE_BALANCE_FLUORESCENT = "fluorescent";
        public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
        public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
        public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
        public static final String WHITE_BALANCE_TWILIGHT = "twilight";
        public static final String WHITE_BALANCE_SHADE = "shade";

        // Values for color effect settings.
        public static final String EFFECT_NONE = "none";
        public static final String EFFECT_MONO = "mono";
        public static final String EFFECT_NEGATIVE = "negative";
        public static final String EFFECT_SOLARIZE = "solarize";
        public static final String EFFECT_SEPIA = "sepia";
        public static final String EFFECT_POSTERIZE = "posterize";
        public static final String EFFECT_WHITEBOARD = "whiteboard";
        public static final String EFFECT_BLACKBOARD = "blackboard";
        public static final String EFFECT_AQUA = "aqua";

        // Values for antibanding settings.
        public static final String ANTIBANDING_AUTO = "auto";
        public static final String ANTIBANDING_50HZ = "50hz";
        public static final String ANTIBANDING_60HZ = "60hz";
        public static final String ANTIBANDING_OFF = "off";

        // Values for flash mode settings.
        /**
         * Flash will not be fired.
         */
        public static final String FLASH_MODE_OFF = "off";

        /**
         * Flash will be fired automatically when required. The flash may be fired
         * during preview, auto-focus, or snapshot depending on the driver.
         */
        public static final String FLASH_MODE_AUTO = "auto";

        /**
         * Flash will always be fired during snapshot. The flash may also be
         * fired during preview or auto-focus depending on the driver.
         */
        public static final String FLASH_MODE_ON = "on";

        /**
         * Flash will be fired in red-eye reduction mode.
         */
        public static final String FLASH_MODE_RED_EYE = "red-eye";

        /**
         * Constant emission of light during preview, auto-focus and snapshot.
         * This can also be used for video recording.
         */
        public static final String FLASH_MODE_TORCH = "torch";

        /**
         * Scene mode is off.
         */
        public static final String SCENE_MODE_AUTO = "auto";

        /**
         * Take photos of fast moving objects. Same as {@link
         * #SCENE_MODE_SPORTS}.
         */
        public static final String SCENE_MODE_ACTION = "action";

        /**
         * Take people pictures.
         */
        public static final String SCENE_MODE_PORTRAIT = "portrait";

        /**
         * Take pictures on distant objects.
         */
        public static final String SCENE_MODE_LANDSCAPE = "landscape";

        /**
         * Take photos at night.
         */
        public static final String SCENE_MODE_NIGHT = "night";

        /**
         * Take people pictures at night.
         */
        public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";

        /**
         * Take photos in a theater. Flash light is off.
         */
        public static final String SCENE_MODE_THEATRE = "theatre";

        /**
         * Take pictures on the beach.
         */
        public static final String SCENE_MODE_BEACH = "beach";

        /**
         * Take pictures on the snow.
         */
        public static final String SCENE_MODE_SNOW = "snow";

        /**
         * Take sunset photos.
         */
        public static final String SCENE_MODE_SUNSET = "sunset";

        /**
         * Avoid blurry pictures (for example, due to hand shake).
         */
        public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";

        /**
         * For shooting firework displays.
         */
        public static final String SCENE_MODE_FIREWORKS = "fireworks";

        /**
         * Take photos of fast moving objects. Same as {@link
         * #SCENE_MODE_ACTION}.
         */
        public static final String SCENE_MODE_SPORTS = "sports";

        /**
         * Take indoor low-light shot.
         */
        public static final String SCENE_MODE_PARTY = "party";

        /**
         * Capture the naturally warm color of scenes lit by candles.
         */
        public static final String SCENE_MODE_CANDLELIGHT = "candlelight";

        /**
         * Applications are looking for a barcode. Camera driver will be
         * optimized for barcode reading.
         */
        public static final String SCENE_MODE_BARCODE = "barcode";

        /**
         * Capture a scene using high dynamic range imaging techniques. The
         * camera will return an image that has an extended dynamic range
         * compared to a regular capture. Capturing such an image may take
         * longer than a regular capture.
         */
        public static final String SCENE_MODE_HDR = "hdr";

        /**
         * Auto-focus mode. Applications should call {@link
         * #autoFocus(AutoFocusCallback)} to start the focus in this mode.
         */
        public static final String FOCUS_MODE_AUTO = "auto";

        /**
         * Focus is set at infinity. Applications should not call
         * {@link #autoFocus(AutoFocusCallback)} in this mode.
         */
        public static final String FOCUS_MODE_INFINITY = "infinity";

        /**
         * Macro (close-up) focus mode. Applications should call
         * {@link #autoFocus(AutoFocusCallback)} to start the focus in this
         * mode.
         */
        public static final String FOCUS_MODE_MACRO = "macro";

        /**
         * Focus is fixed. The camera is always in this mode if the focus is not
         * adjustable. If the camera has auto-focus, this mode can fix the
         * focus, which is usually at hyperfocal distance. Applications should
         * not call {@link #autoFocus(AutoFocusCallback)} in this mode.
         */
        public static final String FOCUS_MODE_FIXED = "fixed";

        /**
         * Extended depth of field (EDOF). Focusing is done digitally and
         * continuously. Applications should not call {@link
         * #autoFocus(AutoFocusCallback)} in this mode.
         */
        public static final String FOCUS_MODE_EDOF = "edof";

        /**
         * Continuous auto focus mode intended for video recording. The camera
         * continuously tries to focus. This is the best choice for video
         * recording because the focus changes smoothly . Applications still can
         * call {@link #takePicture(Camera.ShutterCallback,
         * Camera.PictureCallback, Camera.PictureCallback)} in this mode but the
         * subject may not be in focus. Auto focus starts when the parameter is
         * set.
         *
         * <p>Since API level 14, applications can call {@link
         * #autoFocus(AutoFocusCallback)} in this mode. The focus callback will
         * immediately return with a boolean that indicates whether the focus is
         * sharp or not. The focus position is locked after autoFocus call. If
         * applications want to resume the continuous focus, cancelAutoFocus
         * must be called. Restarting the preview will not resume the continuous
         * autofocus. To stop continuous focus, applications should change the
         * focus mode to other modes.
         *
         * @see #FOCUS_MODE_CONTINUOUS_PICTURE
         */
        public static final String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";

        /**
         * Continuous auto focus mode intended for taking pictures. The camera
         * continuously tries to focus. The speed of focus change is more
         * aggressive than {@link #FOCUS_MODE_CONTINUOUS_VIDEO}. Auto focus
         * starts when the parameter is set.
         *
         * <p>Applications can call {@link #autoFocus(AutoFocusCallback)} in
         * this mode. If the autofocus is in the middle of scanning, the focus
         * callback will return when it completes. If the autofocus is not
         * scanning, the focus callback will immediately return with a boolean
         * that indicates whether the focus is sharp or not. The apps can then
         * decide if they want to take a picture immediately or to change the
         * focus mode to auto, and run a full autofocus cycle. The focus
         * position is locked after autoFocus call. If applications want to
         * resume the continuous focus, cancelAutoFocus must be called.
         * Restarting the preview will not resume the continuous autofocus. To
         * stop continuous focus, applications should change the focus mode to
         * other modes.
         *
         * @see #FOCUS_MODE_CONTINUOUS_VIDEO
         */
        public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";

        // Indices for focus distance array.
        /**
         * The array index of near focus distance for use with
         * {@link #getFocusDistances(float[])}.
         */
        public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;

        /**
         * The array index of optimal focus distance for use with
         * {@link #getFocusDistances(float[])}.
         */
        public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;

        /**
         * The array index of far focus distance for use with
         * {@link #getFocusDistances(float[])}.
         */
        public static final int FOCUS_DISTANCE_FAR_INDEX = 2;

        /**
         * The array index of minimum preview fps for use with {@link
         * #getPreviewFpsRange(int[])} or {@link
         * #getSupportedPreviewFpsRange()}.
         */
        public static final int PREVIEW_FPS_MIN_INDEX = 0;

        /**
         * The array index of maximum preview fps for use with {@link
         * #getPreviewFpsRange(int[])} or {@link
         * #getSupportedPreviewFpsRange()}.
         */
        public static final int PREVIEW_FPS_MAX_INDEX = 1;

        // Formats for setPreviewFormat and setPictureFormat.
        private static final String PIXEL_FORMAT_YUV422SP = "yuv422sp";
        private static final String PIXEL_FORMAT_YUV420SP = "yuv420sp";
        private static final String PIXEL_FORMAT_YUV422I = "yuv422i-yuyv";
        private static final String PIXEL_FORMAT_YUV420P = "yuv420p";
        private static final String PIXEL_FORMAT_RGB565 = "rgb565";
        private static final String PIXEL_FORMAT_JPEG = "jpeg";
        private static final String PIXEL_FORMAT_BAYER_RGGB = "bayer-rggb";

        private HashMap<String, String> mMap;

        private Parameters() {
            mMap = new HashMap<String, String>(64);
        }

        /**
         * Writes the current Parameters to the log.
         * @hide
         * @deprecated
         */
        /*
        public void dump() {
            Log.e(TAG, "dump: size=" + mMap.size());
            for (String k : mMap.keySet()) {
                Log.e(TAG, "dump: " + k + "=" + mMap.get(k));
            }
        }
        */

        /**
         * Creates a single string with all the parameters set in
         * this Parameters object.
         * <p>The {@link #unflatten(String)} method does the reverse.</p>
         *
         * @return a String with all values from this Parameters object, in
         *         semi-colon delimited key-value pairs
         */
        public String flatten() {
            StringBuilder flattened = new StringBuilder(128);
            for (String k : mMap.keySet()) {
                flattened.append(k);
                flattened.append("=");
                flattened.append(mMap.get(k));
                flattened.append(";");
            }
            // chop off the extra semicolon at the end
            flattened.deleteCharAt(flattened.length()-1);
            return flattened.toString();
        }

        /**
         * Takes a flattened string of parameters and adds each one to
         * this Parameters object.
         * <p>The {@link #flatten()} method does the reverse.</p>
         *
         * @param flattened a String of parameters (key-value paired) that
         *                  are semi-colon delimited
         */
        public void unflatten(String flattened) {
            mMap.clear();

            String[] ss = flattened.split(";");
            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(';');
            //splitter.setString(flattened);
            //for (String kv : splitter) {
            for (String kv : ss) {
                int pos = kv.indexOf('=');
                if (pos == -1) {
                    continue;
                }
                String k = kv.substring(0, pos);
                String v = kv.substring(pos + 1);
                mMap.put(k, v);
            }
        }

        public void remove(String key) {
            mMap.remove(key);
        }

        /**
         * Sets a String parameter.
         *
         * @param key   the key name for the parameter
         * @param value the String value of the parameter
         */
        public void set(String key, String value) {
            if (key.indexOf('=') != -1 || key.indexOf(';') != -1 || key.indexOf(0) != -1) {
                //Log.e(TAG, "Key \"" + key + "\" contains invalid character (= or ; or \\0)");
                return;
            }
            if (value.indexOf('=') != -1 || value.indexOf(';') != -1 || value.indexOf(0) != -1) {
                //Log.e(TAG, "Value \"" + value + "\" contains invalid character (= or ; or \\0)");
                return;
            }

            mMap.put(key, value);
        }

        /**
         * Sets an integer parameter.
         *
         * @param key   the key name for the parameter
         * @param value the int value of the parameter
         */
        public void set(String key, int value) {
            mMap.put(key, Integer.toString(value));
        }

        /*
        private void set(String key, List<Area> areas) {
            if (areas == null) {
                set(key, "(0,0,0,0,0)");
            } else {
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < areas.size(); i++) {
                    Area area = areas.get(i);
                    Rect rect = area.rect;
                    buffer.append('(');
                    buffer.append(rect.left);
                    buffer.append(',');
                    buffer.append(rect.top);
                    buffer.append(',');
                    buffer.append(rect.right);
                    buffer.append(',');
                    buffer.append(rect.bottom);
                    buffer.append(',');
                    buffer.append(area.weight);
                    buffer.append(')');
                    if (i != areas.size() - 1) buffer.append(',');
                }
                set(key, buffer.toString());
            }
        }
        */

        /**
         * Returns the value of a String parameter.
         *
         * @param key the key name for the parameter
         * @return the String value of the parameter
         */
        public String get(String key) {
            return mMap.get(key);
        }

        /**
         * Returns the value of an integer parameter.
         *
         * @param key the key name for the parameter
         * @return the int value of the parameter
         */
        public int getInt(String key) {
            return Integer.parseInt(mMap.get(key));
        }

        /**
         * Sets the dimensions for preview pictures. If the preview has already
         * started, applications should stop the preview first before changing
         * preview size.
         *
         * The sides of width and height are based on camera orientation. That
         * is, the preview size is the size before it is rotated by display
         * orientation. So applications need to consider the display orientation
         * while setting preview size. For example, suppose the camera supports
         * both 480x320 and 320x480 preview sizes. The application wants a 3:2
         * preview ratio. If the display orientation is set to 0 or 180, preview
         * size should be set to 480x320. If the display orientation is set to
         * 90 or 270, preview size should be set to 320x480. The display
         * orientation should also be considered while setting picture size and
         * thumbnail size.
         *
         * @param width  the width of the pictures, in pixels
         * @param height the height of the pictures, in pixels
         * @see #setDisplayOrientation(int)
         * @see #getCameraInfo(int, CameraInfo)
         * @see #setPictureSize(int, int)
         * @see #setJpegThumbnailSize(int, int)
         */
        public void setPreviewSize(int width, int height) {
            String v = Integer.toString(width) + "x" + Integer.toString(height);
            set(KEY_PREVIEW_SIZE, v);
        }

        /**
         * Returns the dimensions setting for preview pictures.
         *
         * @return a Size object with the width and height setting
         *          for the preview picture
         */
        public Size getPreviewSize() {
            String pair = get(KEY_PREVIEW_SIZE);
            return strToSize(pair);
        }

        /**
         * Gets the supported preview sizes.
         *
         * @return a list of Size object. This method will always return a list
         *         with at least one element.
         */
        public List<Size> getSupportedPreviewSizes() {
            String str = get(KEY_PREVIEW_SIZE + SUPPORTED_VALUES_SUFFIX);
            return splitSize(str);
        }

        /**
         * <p>Gets the supported video frame sizes that can be used by
         * MediaRecorder.</p>
         *
         * <p>If the returned list is not null, the returned list will contain at
         * least one Size and one of the sizes in the returned list must be
         * passed to MediaRecorder.setVideoSize() for camcorder application if
         * camera is used as the video source. In this case, the size of the
         * preview can be different from the resolution of the recorded video
         * during video recording.</p>
         *
         * @return a list of Size object if camera has separate preview and
         *         video output; otherwise, null is returned.
         * @see #getPreferredPreviewSizeForVideo()
         */
        public List<Size> getSupportedVideoSizes() {
            String str = get(KEY_VIDEO_SIZE + SUPPORTED_VALUES_SUFFIX);
            return splitSize(str);
        }

        /**
         * Returns the preferred or recommended preview size (width and height)
         * in pixels for video recording. Camcorder applications should
         * set the preview size to a value that is not larger than the
         * preferred preview size. In other words, the product of the width
         * and height of the preview size should not be larger than that of
         * the preferred preview size. In addition, we recommend to choose a
         * preview size that has the same aspect ratio as the resolution of
         * video to be recorded.
         *
         * @return the preferred preview size (width and height) in pixels for
         *         video recording if getSupportedVideoSizes() does not return
         *         null; otherwise, null is returned.
         * @see #getSupportedVideoSizes()
         */
        public Size getPreferredPreviewSizeForVideo() {
            String pair = get(KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO);
            return strToSize(pair);
        }

        /**
         * <p>Sets the dimensions for EXIF thumbnail in Jpeg picture. If
         * applications set both width and height to 0, EXIF will not contain
         * thumbnail.</p>
         *
         * <p>Applications need to consider the display orientation. See {@link
         * #setPreviewSize(int,int)} for reference.</p>
         *
         * @param width  the width of the thumbnail, in pixels
         * @param height the height of the thumbnail, in pixels
         * @see #setPreviewSize(int,int)
         */
        public void setJpegThumbnailSize(int width, int height) {
            set(KEY_JPEG_THUMBNAIL_WIDTH, width);
            set(KEY_JPEG_THUMBNAIL_HEIGHT, height);
        }

        /**
         * Returns the dimensions for EXIF thumbnail in Jpeg picture.
         *
         * @return a Size object with the height and width setting for the EXIF
         *         thumbnails
         */
        public Size getJpegThumbnailSize() {
            return new Size(getInt(KEY_JPEG_THUMBNAIL_WIDTH),
                            getInt(KEY_JPEG_THUMBNAIL_HEIGHT));
        }

        /**
         * Gets the supported jpeg thumbnail sizes.
         *
         * @return a list of Size object. This method will always return a list
         *         with at least two elements. Size 0,0 (no thumbnail) is always
         *         supported.
         */
        public List<Size> getSupportedJpegThumbnailSizes() {
            String str = get(KEY_JPEG_THUMBNAIL_SIZE + SUPPORTED_VALUES_SUFFIX);
            return splitSize(str);
        }

        /**
         * Sets the quality of the EXIF thumbnail in Jpeg picture.
         *
         * @param quality the JPEG quality of the EXIF thumbnail. The range is 1
         *                to 100, with 100 being the best.
         */
        public void setJpegThumbnailQuality(int quality) {
            set(KEY_JPEG_THUMBNAIL_QUALITY, quality);
        }

        /**
         * Returns the quality setting for the EXIF thumbnail in Jpeg picture.
         *
         * @return the JPEG quality setting of the EXIF thumbnail.
         */
        public int getJpegThumbnailQuality() {
            return getInt(KEY_JPEG_THUMBNAIL_QUALITY);
        }

        /**
         * Sets Jpeg quality of captured picture.
         *
         * @param quality the JPEG quality of captured picture. The range is 1
         *                to 100, with 100 being the best.
         */
        public void setJpegQuality(int quality) {
            set(KEY_JPEG_QUALITY, quality);
        }

        /**
         * Returns the quality setting for the JPEG picture.
         *
         * @return the JPEG picture quality setting.
         */
        public int getJpegQuality() {
            return getInt(KEY_JPEG_QUALITY);
        }

        /**
         * Sets the rate at which preview frames are received. This is the
         * target frame rate. The actual frame rate depends on the driver.
         *
         * @param fps the frame rate (frames per second)
         * @deprecated replaced by {@link #setPreviewFpsRange(int,int)}
         */
        @Deprecated
        public void setPreviewFrameRate(int fps) {
            set(KEY_PREVIEW_FRAME_RATE, fps);
        }

        /**
         * Returns the setting for the rate at which preview frames are
         * received. This is the target frame rate. The actual frame rate
         * depends on the driver.
         *
         * @return the frame rate setting (frames per second)
         * @deprecated replaced by {@link #getPreviewFpsRange(int[])}
         */
        @Deprecated
        public int getPreviewFrameRate() {
            return getInt(KEY_PREVIEW_FRAME_RATE);
        }

        /**
         * Gets the supported preview frame rates.
         *
         * @return a list of supported preview frame rates. null if preview
         *         frame rate setting is not supported.
         * @deprecated replaced by {@link #getSupportedPreviewFpsRange()}
         */
        @Deprecated
        public List<Integer> getSupportedPreviewFrameRates() {
            String str = get(KEY_PREVIEW_FRAME_RATE + SUPPORTED_VALUES_SUFFIX);
            return splitInt(str);
        }

        /**
         * Sets the minimum and maximum preview fps. This controls the rate of
         * preview frames received in {@link PreviewCallback}. The minimum and
         * maximum preview fps must be one of the elements from {@link
         * #getSupportedPreviewFpsRange}.
         *
         * @param min the minimum preview fps (scaled by 1000).
         * @param max the maximum preview fps (scaled by 1000).
         * @throws RuntimeException if fps range is invalid.
         * @see #setPreviewCallbackWithBuffer(Camera.PreviewCallback)
         * @see #getSupportedPreviewFpsRange()
         */
        public void setPreviewFpsRange(int min, int max) {
            set(KEY_PREVIEW_FPS_RANGE, "" + min + "," + max);
        }

        /**
         * Returns the current minimum and maximum preview fps. The values are
         * one of the elements returned by {@link #getSupportedPreviewFpsRange}.
         *
         * @return range the minimum and maximum preview fps (scaled by 1000).
         * @see #PREVIEW_FPS_MIN_INDEX
         * @see #PREVIEW_FPS_MAX_INDEX
         * @see #getSupportedPreviewFpsRange()
         */
        public void getPreviewFpsRange(int[] range) {
            if (range == null || range.length != 2) {
                throw new IllegalArgumentException(
                        "range must be an array with two elements.");
            }
            splitInt(get(KEY_PREVIEW_FPS_RANGE), range);
        }

        /**
         * Gets the supported preview fps (frame-per-second) ranges. Each range
         * contains a minimum fps and maximum fps. If minimum fps equals to
         * maximum fps, the camera outputs frames in fixed frame rate. If not,
         * the camera outputs frames in auto frame rate. The actual frame rate
         * fluctuates between the minimum and the maximum. The values are
         * multiplied by 1000 and represented in integers. For example, if frame
         * rate is 26.623 frames per second, the value is 26623.
         *
         * @return a list of supported preview fps ranges. This method returns a
         *         list with at least one element. Every element is an int array
         *         of two values - minimum fps and maximum fps. The list is
         *         sorted from small to large (first by maximum fps and then
         *         minimum fps).
         * @see #PREVIEW_FPS_MIN_INDEX
         * @see #PREVIEW_FPS_MAX_INDEX
         */
        public List<int[]> getSupportedPreviewFpsRange() {
            String str = get(KEY_PREVIEW_FPS_RANGE + SUPPORTED_VALUES_SUFFIX);
            return splitRange(str);
        }

        /**
         * Sets the image format for preview pictures.
         * <p>If this is never called, the default format will be
         * {@link android.graphics.ImageFormat#NV21}, which
         * uses the NV21 encoding format.</p>
         *
         * <p>Use {@link Parameters#getSupportedPreviewFormats} to get a list of
         * the available preview formats.
         *
         * <p>It is strongly recommended that either
         * {@link android.graphics.ImageFormat#NV21} or
         * {@link android.graphics.ImageFormat#YV12} is used, since
         * they are supported by all camera devices.</p>
         *
         * <p>For YV12, the image buffer that is received is not necessarily
         * tightly packed, as there may be padding at the end of each row of
         * pixel data, as described in
         * {@link android.graphics.ImageFormat#YV12}. For camera callback data,
         * it can be assumed that the stride of the Y and UV data is the
         * smallest possible that meets the alignment requirements. That is, if
         * the preview size is <var>width x height</var>, then the following
         * equations describe the buffer index for the beginning of row
         * <var>y</var> for the Y plane and row <var>c</var> for the U and V
         * planes:
         *
         * {@code
         * <pre>
         * yStride   = (int) ceil(width / 16.0) * 16;
         * uvStride  = (int) ceil( (yStride / 2) / 16.0) * 16;
         * ySize     = yStride * height;
         * uvSize    = uvStride * height / 2;
         * yRowIndex = yStride * y;
         * uRowIndex = ySize + uvSize + uvStride * c;
         * vRowIndex = ySize + uvStride * c;
         * size      = ySize + uvSize * 2;</pre>
         * }
         *
         * @param pixel_format the desired preview picture format, defined by
         *   one of the {@link android.graphics.ImageFormat} constants.  (E.g.,
         *   <var>ImageFormat.NV21</var> (default), or
         *   <var>ImageFormat.YV12</var>)
         *
         * @see android.graphics.ImageFormat
         * @see android.hardware.Camera.Parameters#getSupportedPreviewFormats
         */
        public void setPreviewFormat(int pixel_format) {
            String s = cameraFormatForPixelFormat(pixel_format);
            if (s == null) {
                throw new IllegalArgumentException(
                        "Invalid pixel_format=" + pixel_format);
            }

            set(KEY_PREVIEW_FORMAT, s);
        }

        /**
         * Returns the image format for preview frames got from
         * {@link PreviewCallback}.
         *
         * @return the preview format.
         * @see android.graphics.ImageFormat
         * @see #setPreviewFormat
         */
        public int getPreviewFormat() {
            return pixelFormatForCameraFormat(get(KEY_PREVIEW_FORMAT));
        }

        /**
         * Gets the supported preview formats. {@link android.graphics.ImageFormat#NV21}
         * is always supported. {@link android.graphics.ImageFormat#YV12}
         * is always supported since API level 12.
         *
         * @return a list of supported preview formats. This method will always
         *         return a list with at least one element.
         * @see android.graphics.ImageFormat
         * @see #setPreviewFormat
         */
        public List<Integer> getSupportedPreviewFormats() {
            String str = get(KEY_PREVIEW_FORMAT + SUPPORTED_VALUES_SUFFIX);
            ArrayList<Integer> formats = new ArrayList<Integer>();
            for (String s : split(str)) {
                int f = pixelFormatForCameraFormat(s);
                if (f == ImageFormat.UNKNOWN) continue;
                formats.add(f);
            }
            return formats;
        }

        /**
         * <p>Sets the dimensions for pictures.</p>
         *
         * <p>Applications need to consider the display orientation. See {@link
         * #setPreviewSize(int,int)} for reference.</p>
         *
         * @param width  the width for pictures, in pixels
         * @param height the height for pictures, in pixels
         * @see #setPreviewSize(int,int)
         *
         */
        public void setPictureSize(int width, int height) {
            String v = Integer.toString(width) + "x" + Integer.toString(height);
            set(KEY_PICTURE_SIZE, v);
        }

        /**
         * Returns the dimension setting for pictures.
         *
         * @return a Size object with the height and width setting
         *          for pictures
         */
        public Size getPictureSize() {
            String pair = get(KEY_PICTURE_SIZE);
            return strToSize(pair);
        }

        /**
         * Gets the supported picture sizes.
         *
         * @return a list of supported picture sizes. This method will always
         *         return a list with at least one element.
         */
        public List<Size> getSupportedPictureSizes() {
            String str = get(KEY_PICTURE_SIZE + SUPPORTED_VALUES_SUFFIX);
            return splitSize(str);
        }

        /**
         * Sets the image format for pictures.
         *
         * @param pixel_format the desired picture format
         *                     (<var>ImageFormat.NV21</var>,
         *                      <var>ImageFormat.RGB_565</var>, or
         *                      <var>ImageFormat.JPEG</var>)
         * @see android.graphics.ImageFormat
         */
        public void setPictureFormat(int pixel_format) {
            String s = cameraFormatForPixelFormat(pixel_format);
            if (s == null) {
                throw new IllegalArgumentException(
                        "Invalid pixel_format=" + pixel_format);
            }

            set(KEY_PICTURE_FORMAT, s);
        }

        /**
         * Returns the image format for pictures.
         *
         * @return the picture format
         * @see android.graphics.ImageFormat
         */
        public int getPictureFormat() {
            return pixelFormatForCameraFormat(get(KEY_PICTURE_FORMAT));
        }

        /**
         * Gets the supported picture formats.
         *
         * @return supported picture formats. This method will always return a
         *         list with at least one element.
         * @see android.graphics.ImageFormat
         */
        public List<Integer> getSupportedPictureFormats() {
            String str = get(KEY_PICTURE_FORMAT + SUPPORTED_VALUES_SUFFIX);
            ArrayList<Integer> formats = new ArrayList<Integer>();
            for (String s : split(str)) {
                int f = pixelFormatForCameraFormat(s);
                if (f == ImageFormat.UNKNOWN) continue;
                formats.add(f);
            }
            return formats;
        }

        private String cameraFormatForPixelFormat(int pixel_format) {
            switch(pixel_format) {
            case ImageFormat.NV16:      return PIXEL_FORMAT_YUV422SP;
            case ImageFormat.NV21:      return PIXEL_FORMAT_YUV420SP;
            case ImageFormat.YUY2:      return PIXEL_FORMAT_YUV422I;
            case ImageFormat.YV12:      return PIXEL_FORMAT_YUV420P;
            case ImageFormat.RGB_565:   return PIXEL_FORMAT_RGB565;
            case ImageFormat.JPEG:      return PIXEL_FORMAT_JPEG;
            case ImageFormat.BAYER_RGGB: return PIXEL_FORMAT_BAYER_RGGB;
            default:                    return null;
            }
        }

        private int pixelFormatForCameraFormat(String format) {
            if (format == null)
                return ImageFormat.UNKNOWN;

            if (format.equals(PIXEL_FORMAT_YUV422SP))
                return ImageFormat.NV16;

            if (format.equals(PIXEL_FORMAT_YUV420SP))
                return ImageFormat.NV21;

            if (format.equals(PIXEL_FORMAT_YUV422I))
                return ImageFormat.YUY2;

            if (format.equals(PIXEL_FORMAT_YUV420P))
                return ImageFormat.YV12;

            if (format.equals(PIXEL_FORMAT_RGB565))
                return ImageFormat.RGB_565;

            if (format.equals(PIXEL_FORMAT_JPEG))
                return ImageFormat.JPEG;

            return ImageFormat.UNKNOWN;
        }

        /**
         * Sets the clockwise rotation angle in degrees relative to the
         * orientation of the camera. This affects the pictures returned from
         * JPEG {@link PictureCallback}. The camera driver may set orientation
         * in the EXIF header without rotating the picture. Or the driver may
         * rotate the picture and the EXIF thumbnail. If the Jpeg picture is
         * rotated, the orientation in the EXIF header will be missing or 1
         * (row #0 is top and column #0 is left side).
         *
         * <p>If applications want to rotate the picture to match the orientation
         * of what users see, apps should use {@link
         * android.view.OrientationEventListener} and {@link CameraInfo}.
         * The value from OrientationEventListener is relative to the natural
         * orientation of the device. CameraInfo.orientation is the angle
         * between camera orientation and natural device orientation. The sum
         * of the two is the rotation angle for back-facing camera. The
         * difference of the two is the rotation angle for front-facing camera.
         * Note that the JPEG pictures of front-facing cameras are not mirrored
         * as in preview display.
         *
         * <p>For example, suppose the natural orientation of the device is
         * portrait. The device is rotated 270 degrees clockwise, so the device
         * orientation is 270. Suppose a back-facing camera sensor is mounted in
         * landscape and the top side of the camera sensor is aligned with the
         * right edge of the display in natural orientation. So the camera
         * orientation is 90. The rotation should be set to 0 (270 + 90).
         *
         * <p>The reference code is as follows.
         *
         * <pre>
         * public void onOrientationChanged(int orientation) {
         *     if (orientation == ORIENTATION_UNKNOWN) return;
         *     android.hardware.Camera.CameraInfo info =
         *            new android.hardware.Camera.CameraInfo();
         *     android.hardware.Camera.getCameraInfo(cameraId, info);
         *     orientation = (orientation + 45) / 90 * 90;
         *     int rotation = 0;
         *     if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
         *         rotation = (info.orientation - orientation + 360) % 360;
         *     } else {  // back-facing camera
         *         rotation = (info.orientation + orientation) % 360;
         *     }
         *     mParameters.setRotation(rotation);
         * }
         * </pre>
         *
         * @param rotation The rotation angle in degrees relative to the
         *                 orientation of the camera. Rotation can only be 0,
         *                 90, 180 or 270.
         * @throws IllegalArgumentException if rotation value is invalid.
         * @see android.view.OrientationEventListener
         * @see #getCameraInfo(int, CameraInfo)
         */
        public void setRotation(int rotation) {
            if (rotation == 0 || rotation == 90 || rotation == 180
                    || rotation == 270) {
                set(KEY_ROTATION, Integer.toString(rotation));
            } else {
                throw new IllegalArgumentException(
                        "Invalid rotation=" + rotation);
            }
        }

        /**
         * Sets GPS latitude coordinate. This will be stored in JPEG EXIF
         * header.
         *
         * @param latitude GPS latitude coordinate.
         */
        public void setGpsLatitude(double latitude) {
            set(KEY_GPS_LATITUDE, Double.toString(latitude));
        }

        /**
         * Sets GPS longitude coordinate. This will be stored in JPEG EXIF
         * header.
         *
         * @param longitude GPS longitude coordinate.
         */
        public void setGpsLongitude(double longitude) {
            set(KEY_GPS_LONGITUDE, Double.toString(longitude));
        }

        /**
         * Sets GPS altitude. This will be stored in JPEG EXIF header.
         *
         * @param altitude GPS altitude in meters.
         */
        public void setGpsAltitude(double altitude) {
            set(KEY_GPS_ALTITUDE, Double.toString(altitude));
        }

        /**
         * Sets GPS timestamp. This will be stored in JPEG EXIF header.
         *
         * @param timestamp GPS timestamp (UTC in seconds since January 1,
         *                  1970).
         */
        public void setGpsTimestamp(long timestamp) {
            set(KEY_GPS_TIMESTAMP, Long.toString(timestamp));
        }

        /**
         * Sets GPS processing method. It will store up to 32 characters
         * in JPEG EXIF header.
         *
         * @param processing_method The processing method to get this location.
         */
        public void setGpsProcessingMethod(String processing_method) {
            set(KEY_GPS_PROCESSING_METHOD, processing_method);
        }

        /**
         * Removes GPS latitude, longitude, altitude, and timestamp from the
         * parameters.
         */
        public void removeGpsData() {
            remove(KEY_GPS_LATITUDE);
            remove(KEY_GPS_LONGITUDE);
            remove(KEY_GPS_ALTITUDE);
            remove(KEY_GPS_TIMESTAMP);
            remove(KEY_GPS_PROCESSING_METHOD);
        }

        /**
         * Gets the current white balance setting.
         *
         * @return current white balance. null if white balance setting is not
         *         supported.
         * @see #WHITE_BALANCE_AUTO
         * @see #WHITE_BALANCE_INCANDESCENT
         * @see #WHITE_BALANCE_FLUORESCENT
         * @see #WHITE_BALANCE_WARM_FLUORESCENT
         * @see #WHITE_BALANCE_DAYLIGHT
         * @see #WHITE_BALANCE_CLOUDY_DAYLIGHT
         * @see #WHITE_BALANCE_TWILIGHT
         * @see #WHITE_BALANCE_SHADE
         *
         */
        public String getWhiteBalance() {
            return get(KEY_WHITE_BALANCE);
        }

        /**
         * Sets the white balance. Changing the setting will release the
         * auto-white balance lock. It is recommended not to change white
         * balance and AWB lock at the same time.
         *
         * @param value new white balance.
         * @see #getWhiteBalance()
         * @see #setAutoWhiteBalanceLock(boolean)
         */
        public void setWhiteBalance(String value) {
            String oldValue = get(KEY_WHITE_BALANCE);
            if (same(value, oldValue)) return;
            set(KEY_WHITE_BALANCE, value);
            set(KEY_AUTO_WHITEBALANCE_LOCK, FALSE);
        }

        /**
         * Gets the supported white balance.
         *
         * @return a list of supported white balance. null if white balance
         *         setting is not supported.
         * @see #getWhiteBalance()
         */
        public List<String> getSupportedWhiteBalance() {
            String str = get(KEY_WHITE_BALANCE + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }

        /**
         * Gets the current color effect setting.
         *
         * @return current color effect. null if color effect
         *         setting is not supported.
         * @see #EFFECT_NONE
         * @see #EFFECT_MONO
         * @see #EFFECT_NEGATIVE
         * @see #EFFECT_SOLARIZE
         * @see #EFFECT_SEPIA
         * @see #EFFECT_POSTERIZE
         * @see #EFFECT_WHITEBOARD
         * @see #EFFECT_BLACKBOARD
         * @see #EFFECT_AQUA
         */
        public String getColorEffect() {
            return get(KEY_EFFECT);
        }

        /**
         * Sets the current color effect setting.
         *
         * @param value new color effect.
         * @see #getColorEffect()
         */
        public void setColorEffect(String value) {
            set(KEY_EFFECT, value);
        }

        /**
         * Gets the supported color effects.
         *
         * @return a list of supported color effects. null if color effect
         *         setting is not supported.
         * @see #getColorEffect()
         */
        public List<String> getSupportedColorEffects() {
            String str = get(KEY_EFFECT + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }


        /**
         * Gets the current antibanding setting.
         *
         * @return current antibanding. null if antibanding setting is not
         *         supported.
         * @see #ANTIBANDING_AUTO
         * @see #ANTIBANDING_50HZ
         * @see #ANTIBANDING_60HZ
         * @see #ANTIBANDING_OFF
         */
        public String getAntibanding() {
            return get(KEY_ANTIBANDING);
        }

        /**
         * Sets the antibanding.
         *
         * @param antibanding new antibanding value.
         * @see #getAntibanding()
         */
        public void setAntibanding(String antibanding) {
            set(KEY_ANTIBANDING, antibanding);
        }

        /**
         * Gets the supported antibanding values.
         *
         * @return a list of supported antibanding values. null if antibanding
         *         setting is not supported.
         * @see #getAntibanding()
         */
        public List<String> getSupportedAntibanding() {
            String str = get(KEY_ANTIBANDING + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }

        /**
         * Gets the current scene mode setting.
         *
         * @return one of SCENE_MODE_XXX string constant. null if scene mode
         *         setting is not supported.
         * @see #SCENE_MODE_AUTO
         * @see #SCENE_MODE_ACTION
         * @see #SCENE_MODE_PORTRAIT
         * @see #SCENE_MODE_LANDSCAPE
         * @see #SCENE_MODE_NIGHT
         * @see #SCENE_MODE_NIGHT_PORTRAIT
         * @see #SCENE_MODE_THEATRE
         * @see #SCENE_MODE_BEACH
         * @see #SCENE_MODE_SNOW
         * @see #SCENE_MODE_SUNSET
         * @see #SCENE_MODE_STEADYPHOTO
         * @see #SCENE_MODE_FIREWORKS
         * @see #SCENE_MODE_SPORTS
         * @see #SCENE_MODE_PARTY
         * @see #SCENE_MODE_CANDLELIGHT
         * @see #SCENE_MODE_BARCODE
         */
        public String getSceneMode() {
            return get(KEY_SCENE_MODE);
        }

        /**
         * Sets the scene mode. Changing scene mode may override other
         * parameters (such as flash mode, focus mode, white balance). For
         * example, suppose originally flash mode is on and supported flash
         * modes are on/off. In night scene mode, both flash mode and supported
         * flash mode may be changed to off. After setting scene mode,
         * applications should call getParameters to know if some parameters are
         * changed.
         *
         * @param value scene mode.
         * @see #getSceneMode()
         */
        public void setSceneMode(String value) {
            set(KEY_SCENE_MODE, value);
        }

        /**
         * Gets the supported scene modes.
         *
         * @return a list of supported scene modes. null if scene mode setting
         *         is not supported.
         * @see #getSceneMode()
         */
        public List<String> getSupportedSceneModes() {
            String str = get(KEY_SCENE_MODE + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }

        /**
         * Gets the current flash mode setting.
         *
         * @return current flash mode. null if flash mode setting is not
         *         supported.
         * @see #FLASH_MODE_OFF
         * @see #FLASH_MODE_AUTO
         * @see #FLASH_MODE_ON
         * @see #FLASH_MODE_RED_EYE
         * @see #FLASH_MODE_TORCH
         */
        public String getFlashMode() {
            return get(KEY_FLASH_MODE);
        }

        /**
         * Sets the flash mode.
         *
         * @param value flash mode.
         * @see #getFlashMode()
         */
        public void setFlashMode(String value) {
            set(KEY_FLASH_MODE, value);
        }

        /**
         * Gets the supported flash modes.
         *
         * @return a list of supported flash modes. null if flash mode setting
         *         is not supported.
         * @see #getFlashMode()
         */
        public List<String> getSupportedFlashModes() {
            String str = get(KEY_FLASH_MODE + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }

        /**
         * Gets the current focus mode setting.
         *
         * @return current focus mode. This method will always return a non-null
         *         value. Applications should call {@link
         *         #autoFocus(AutoFocusCallback)} to start the focus if focus
         *         mode is FOCUS_MODE_AUTO or FOCUS_MODE_MACRO.
         * @see #FOCUS_MODE_AUTO
         * @see #FOCUS_MODE_INFINITY
         * @see #FOCUS_MODE_MACRO
         * @see #FOCUS_MODE_FIXED
         * @see #FOCUS_MODE_EDOF
         * @see #FOCUS_MODE_CONTINUOUS_VIDEO
         */
        public String getFocusMode() {
            return get(KEY_FOCUS_MODE);
        }

        /**
         * Sets the focus mode.
         *
         * @param value focus mode.
         * @see #getFocusMode()
         */
        public void setFocusMode(String value) {
            set(KEY_FOCUS_MODE, value);
        }

        /**
         * Gets the supported focus modes.
         *
         * @return a list of supported focus modes. This method will always
         *         return a list with at least one element.
         * @see #getFocusMode()
         */
        public List<String> getSupportedFocusModes() {
            String str = get(KEY_FOCUS_MODE + SUPPORTED_VALUES_SUFFIX);
            return split(str);
        }

        /**
         * Gets the focal length (in millimeter) of the camera.
         *
         * @return the focal length. This method will always return a valid
         *         value.
         */
        public float getFocalLength() {
            return Float.parseFloat(get(KEY_FOCAL_LENGTH));
        }

        /**
         * Gets the horizontal angle of view in degrees.
         *
         * @return horizontal angle of view. This method will always return a
         *         valid value.
         */
        public float getHorizontalViewAngle() {
            return Float.parseFloat(get(KEY_HORIZONTAL_VIEW_ANGLE));
        }

        /**
         * Gets the vertical angle of view in degrees.
         *
         * @return vertical angle of view. This method will always return a
         *         valid value.
         */
        public float getVerticalViewAngle() {
            return Float.parseFloat(get(KEY_VERTICAL_VIEW_ANGLE));
        }

        /**
         * Gets the current exposure compensation index.
         *
         * @return current exposure compensation index. The range is {@link
         *         #getMinExposureCompensation} to {@link
         *         #getMaxExposureCompensation}. 0 means exposure is not
         *         adjusted.
         */
        public int getExposureCompensation() {
            return getInt(KEY_EXPOSURE_COMPENSATION, 0);
        }

        /**
         * Sets the exposure compensation index.
         *
         * @param value exposure compensation index. The valid value range is
         *        from {@link #getMinExposureCompensation} (inclusive) to {@link
         *        #getMaxExposureCompensation} (inclusive). 0 means exposure is
         *        not adjusted. Application should call
         *        getMinExposureCompensation and getMaxExposureCompensation to
         *        know if exposure compensation is supported.
         */
        public void setExposureCompensation(int value) {
            set(KEY_EXPOSURE_COMPENSATION, value);
        }

        /**
         * Gets the maximum exposure compensation index.
         *
         * @return maximum exposure compensation index (>=0). If both this
         *         method and {@link #getMinExposureCompensation} return 0,
         *         exposure compensation is not supported.
         */
        public int getMaxExposureCompensation() {
            return getInt(KEY_MAX_EXPOSURE_COMPENSATION, 0);
        }

        /**
         * Gets the minimum exposure compensation index.
         *
         * @return minimum exposure compensation index (<=0). If both this
         *         method and {@link #getMaxExposureCompensation} return 0,
         *         exposure compensation is not supported.
         */
        public int getMinExposureCompensation() {
            return getInt(KEY_MIN_EXPOSURE_COMPENSATION, 0);
        }

        /**
         * Gets the exposure compensation step.
         *
         * @return exposure compensation step. Applications can get EV by
         *         multiplying the exposure compensation index and step. Ex: if
         *         exposure compensation index is -6 and step is 0.333333333, EV
         *         is -2.
         */
        public float getExposureCompensationStep() {
            return getFloat(KEY_EXPOSURE_COMPENSATION_STEP, 0);
        }

        /**
         * <p>Sets the auto-exposure lock state. Applications should check
         * {@link #isAutoExposureLockSupported} before using this method.</p>
         *
         * <p>If set to true, the camera auto-exposure routine will immediately
         * pause until the lock is set to false. Exposure compensation settings
         * changes will still take effect while auto-exposure is locked.</p>
         *
         * <p>If auto-exposure is already locked, setting this to true again has
         * no effect (the driver will not recalculate exposure values).</p>
         *
         * <p>Stopping preview with {@link #stopPreview()}, or triggering still
         * image capture with {@link #takePicture(Camera.ShutterCallback,
         * Camera.PictureCallback, Camera.PictureCallback)}, will not change the
         * lock.</p>
         *
         * <p>Exposure compensation, auto-exposure lock, and auto-white balance
         * lock can be used to capture an exposure-bracketed burst of images,
         * for example.</p>
         *
         * <p>Auto-exposure state, including the lock state, will not be
         * maintained after camera {@link #release()} is called.  Locking
         * auto-exposure after {@link #open()} but before the first call to
         * {@link #startPreview()} will not allow the auto-exposure routine to
         * run at all, and may result in severely over- or under-exposed
         * images.</p>
         *
         * @param toggle new state of the auto-exposure lock. True means that
         *        auto-exposure is locked, false means that the auto-exposure
         *        routine is free to run normally.
         *
         * @see #getAutoExposureLock()
         */
        public void setAutoExposureLock(boolean toggle) {
            set(KEY_AUTO_EXPOSURE_LOCK, toggle ? TRUE : FALSE);
        }

        /**
         * Gets the state of the auto-exposure lock. Applications should check
         * {@link #isAutoExposureLockSupported} before using this method. See
         * {@link #setAutoExposureLock} for details about the lock.
         *
         * @return State of the auto-exposure lock. Returns true if
         *         auto-exposure is currently locked, and false otherwise.
         *
         * @see #setAutoExposureLock(boolean)
         *
         */
        public boolean getAutoExposureLock() {
            String str = get(KEY_AUTO_EXPOSURE_LOCK);
            return TRUE.equals(str);
        }

        /**
         * Returns true if auto-exposure locking is supported. Applications
         * should call this before trying to lock auto-exposure. See
         * {@link #setAutoExposureLock} for details about the lock.
         *
         * @return true if auto-exposure lock is supported.
         * @see #setAutoExposureLock(boolean)
         *
         */
        public boolean isAutoExposureLockSupported() {
            String str = get(KEY_AUTO_EXPOSURE_LOCK_SUPPORTED);
            return TRUE.equals(str);
        }

        /**
         * <p>Sets the auto-white balance lock state. Applications should check
         * {@link #isAutoWhiteBalanceLockSupported} before using this
         * method.</p>
         *
         * <p>If set to true, the camera auto-white balance routine will
         * immediately pause until the lock is set to false.</p>
         *
         * <p>If auto-white balance is already locked, setting this to true
         * again has no effect (the driver will not recalculate white balance
         * values).</p>
         *
         * <p>Stopping preview with {@link #stopPreview()}, or triggering still
         * image capture with {@link #takePicture(Camera.ShutterCallback,
         * Camera.PictureCallback, Camera.PictureCallback)}, will not change the
         * the lock.</p>
         *
         * <p> Changing the white balance mode with {@link #setWhiteBalance}
         * will release the auto-white balance lock if it is set.</p>
         *
         * <p>Exposure compensation, AE lock, and AWB lock can be used to
         * capture an exposure-bracketed burst of images, for example.
         * Auto-white balance state, including the lock state, will not be
         * maintained after camera {@link #release()} is called.  Locking
         * auto-white balance after {@link #open()} but before the first call to
         * {@link #startPreview()} will not allow the auto-white balance routine
         * to run at all, and may result in severely incorrect color in captured
         * images.</p>
         *
         * @param toggle new state of the auto-white balance lock. True means
         *        that auto-white balance is locked, false means that the
         *        auto-white balance routine is free to run normally.
         *
         * @see #getAutoWhiteBalanceLock()
         * @see #setWhiteBalance(String)
         */
        public void setAutoWhiteBalanceLock(boolean toggle) {
            set(KEY_AUTO_WHITEBALANCE_LOCK, toggle ? TRUE : FALSE);
        }

        /**
         * Gets the state of the auto-white balance lock. Applications should
         * check {@link #isAutoWhiteBalanceLockSupported} before using this
         * method. See {@link #setAutoWhiteBalanceLock} for details about the
         * lock.
         *
         * @return State of the auto-white balance lock. Returns true if
         *         auto-white balance is currently locked, and false
         *         otherwise.
         *
         * @see #setAutoWhiteBalanceLock(boolean)
         *
         */
        public boolean getAutoWhiteBalanceLock() {
            String str = get(KEY_AUTO_WHITEBALANCE_LOCK);
            return TRUE.equals(str);
        }

        /**
         * Returns true if auto-white balance locking is supported. Applications
         * should call this before trying to lock auto-white balance. See
         * {@link #setAutoWhiteBalanceLock} for details about the lock.
         *
         * @return true if auto-white balance lock is supported.
         * @see #setAutoWhiteBalanceLock(boolean)
         *
         */
        public boolean isAutoWhiteBalanceLockSupported() {
            String str = get(KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED);
            return TRUE.equals(str);
        }

        /**
         * Gets current zoom value. This also works when smooth zoom is in
         * progress. Applications should check {@link #isZoomSupported} before
         * using this method.
         *
         * @return the current zoom value. The range is 0 to {@link
         *         #getMaxZoom}. 0 means the camera is not zoomed.
         */
        public int getZoom() {
            return getInt(KEY_ZOOM, 0);
        }

        /**
         * Sets current zoom value. If the camera is zoomed (value > 0), the
         * actual picture size may be smaller than picture size setting.
         * Applications can check the actual picture size after picture is
         * returned from {@link PictureCallback}. The preview size remains the
         * same in zoom. Applications should check {@link #isZoomSupported}
         * before using this method.
         *
         * @param value zoom value. The valid range is 0 to {@link #getMaxZoom}.
         */
        public void setZoom(int value) {
            set(KEY_ZOOM, value);
        }

        /**
         * Returns true if zoom is supported. Applications should call this
         * before using other zoom methods.
         *
         * @return true if zoom is supported.
         */
        public boolean isZoomSupported() {
            String str = get(KEY_ZOOM_SUPPORTED);
            return TRUE.equals(str);
        }

        /**
         * Gets the maximum zoom value allowed for snapshot. This is the maximum
         * value that applications can set to {@link #setZoom(int)}.
         * Applications should call {@link #isZoomSupported} before using this
         * method. This value may change in different preview size. Applications
         * should call this again after setting preview size.
         *
         * @return the maximum zoom value supported by the camera.
         */
        public int getMaxZoom() {
            return getInt(KEY_MAX_ZOOM, 0);
        }

        /**
         * Gets the zoom ratios of all zoom values. Applications should check
         * {@link #isZoomSupported} before using this method.
         *
         * @return the zoom ratios in 1/100 increments. Ex: a zoom of 3.2x is
         *         returned as 320. The number of elements is {@link
         *         #getMaxZoom} + 1. The list is sorted from small to large. The
         *         first element is always 100. The last element is the zoom
         *         ratio of the maximum zoom value.
         */
        public List<Integer> getZoomRatios() {
            return splitInt(get(KEY_ZOOM_RATIOS));
        }

        /**
         * Returns true if smooth zoom is supported. Applications should call
         * this before using other smooth zoom methods.
         *
         * @return true if smooth zoom is supported.
         */
        public boolean isSmoothZoomSupported() {
            String str = get(KEY_SMOOTH_ZOOM_SUPPORTED);
            return TRUE.equals(str);
        }

        /**
         * <p>Gets the distances from the camera to where an object appears to be
         * in focus. The object is sharpest at the optimal focus distance. The
         * depth of field is the far focus distance minus near focus distance.</p>
         *
         * <p>Focus distances may change after calling {@link
         * #autoFocus(AutoFocusCallback)}, {@link #cancelAutoFocus}, or {@link
         * #startPreview()}. Applications can call {@link #getParameters()}
         * and this method anytime to get the latest focus distances. If the
         * focus mode is FOCUS_MODE_CONTINUOUS_VIDEO, focus distances may change
         * from time to time.</p>
         *
         * <p>This method is intended to estimate the distance between the camera
         * and the subject. After autofocus, the subject distance may be within
         * near and far focus distance. However, the precision depends on the
         * camera hardware, autofocus algorithm, the focus area, and the scene.
         * The error can be large and it should be only used as a reference.</p>
         *
         * <p>Far focus distance >= optimal focus distance >= near focus distance.
         * If the focus distance is infinity, the value will be
         * {@code Float.POSITIVE_INFINITY}.</p>
         *
         * @param output focus distances in meters. output must be a float
         *        array with three elements. Near focus distance, optimal focus
         *        distance, and far focus distance will be filled in the array.
         * @see #FOCUS_DISTANCE_NEAR_INDEX
         * @see #FOCUS_DISTANCE_OPTIMAL_INDEX
         * @see #FOCUS_DISTANCE_FAR_INDEX
         */
        public void getFocusDistances(float[] output) {
            if (output == null || output.length != 3) {
                throw new IllegalArgumentException(
                        "output must be a float array with three elements.");
            }
            splitFloat(get(KEY_FOCUS_DISTANCES), output);
        }

        /**
         * Gets the maximum number of focus areas supported. This is the maximum
         * length of the list in {@link #setFocusAreas(List)} and
         * {@link #getFocusAreas()}.
         *
         * @return the maximum number of focus areas supported by the camera.
         * @see #getFocusAreas()
         */
        public int getMaxNumFocusAreas() {
            return getInt(KEY_MAX_NUM_FOCUS_AREAS, 0);
        }

        /**
         * <p>Gets the current focus areas. Camera driver uses the areas to decide
         * focus.</p>
         *
         * <p>Before using this API or {@link #setFocusAreas(List)}, apps should
         * call {@link #getMaxNumFocusAreas()} to know the maximum number of
         * focus areas first. If the value is 0, focus area is not supported.</p>
         *
         * <p>Each focus area is a rectangle with specified weight. The direction
         * is relative to the sensor orientation, that is, what the sensor sees.
         * The direction is not affected by the rotation or mirroring of
         * {@link #setDisplayOrientation(int)}. Coordinates of the rectangle
         * range from -1000 to 1000. (-1000, -1000) is the upper left point.
         * (1000, 1000) is the lower right point. The width and height of focus
         * areas cannot be 0 or negative.</p>
         *
         * <p>The weight must range from 1 to 1000. The weight should be
         * interpreted as a per-pixel weight - all pixels in the area have the
         * specified weight. This means a small area with the same weight as a
         * larger area will have less influence on the focusing than the larger
         * area. Focus areas can partially overlap and the driver will add the
         * weights in the overlap region.</p>
         *
         * <p>A special case of a {@code null} focus area list means the driver is
         * free to select focus targets as it wants. For example, the driver may
         * use more signals to select focus areas and change them
         * dynamically. Apps can set the focus area list to {@code null} if they
         * want the driver to completely control focusing.</p>
         *
         * <p>Focus areas are relative to the current field of view
         * ({@link #getZoom()}). No matter what the zoom level is, (-1000,-1000)
         * represents the top of the currently visible camera frame. The focus
         * area cannot be set to be outside the current field of view, even
         * when using zoom.</p>
         *
         * <p>Focus area only has effect if the current focus mode is
         * {@link #FOCUS_MODE_AUTO}, {@link #FOCUS_MODE_MACRO},
         * {@link #FOCUS_MODE_CONTINUOUS_VIDEO}, or
         * {@link #FOCUS_MODE_CONTINUOUS_PICTURE}.</p>
         *
         * @return a list of current focus areas
         */
        public List<Area> getFocusAreas() {
            return splitArea(get(KEY_FOCUS_AREAS));
        }

        /**
         * Sets focus areas. See {@link #getFocusAreas()} for documentation.
         *
         * @param focusAreas the focus areas
         * @see #getFocusAreas()
         */
        public void setFocusAreas(List<Area> focusAreas) {
            //set(KEY_FOCUS_AREAS, focusAreas);
        }

        /**
         * Gets the maximum number of metering areas supported. This is the
         * maximum length of the list in {@link #setMeteringAreas(List)} and
         * {@link #getMeteringAreas()}.
         *
         * @return the maximum number of metering areas supported by the camera.
         * @see #getMeteringAreas()
         */
        public int getMaxNumMeteringAreas() {
            return getInt(KEY_MAX_NUM_METERING_AREAS, 0);
        }

        /**
         * <p>Gets the current metering areas. Camera driver uses these areas to
         * decide exposure.</p>
         *
         * <p>Before using this API or {@link #setMeteringAreas(List)}, apps should
         * call {@link #getMaxNumMeteringAreas()} to know the maximum number of
         * metering areas first. If the value is 0, metering area is not
         * supported.</p>
         *
         * <p>Each metering area is a rectangle with specified weight. The
         * direction is relative to the sensor orientation, that is, what the
         * sensor sees. The direction is not affected by the rotation or
         * mirroring of {@link #setDisplayOrientation(int)}. Coordinates of the
         * rectangle range from -1000 to 1000. (-1000, -1000) is the upper left
         * point. (1000, 1000) is the lower right point. The width and height of
         * metering areas cannot be 0 or negative.</p>
         *
         * <p>The weight must range from 1 to 1000, and represents a weight for
         * every pixel in the area. This means that a large metering area with
         * the same weight as a smaller area will have more effect in the
         * metering result.  Metering areas can partially overlap and the driver
         * will add the weights in the overlap region.</p>
         *
         * <p>A special case of a {@code null} metering area list means the driver
         * is free to meter as it chooses. For example, the driver may use more
         * signals to select metering areas and change them dynamically. Apps
         * can set the metering area list to {@code null} if they want the
         * driver to completely control metering.</p>
         *
         * <p>Metering areas are relative to the current field of view
         * ({@link #getZoom()}). No matter what the zoom level is, (-1000,-1000)
         * represents the top of the currently visible camera frame. The
         * metering area cannot be set to be outside the current field of view,
         * even when using zoom.</p>
         *
         * <p>No matter what metering areas are, the final exposure are compensated
         * by {@link #setExposureCompensation(int)}.</p>
         *
         * @return a list of current metering areas
         */
        public List<Area> getMeteringAreas() {
            return splitArea(get(KEY_METERING_AREAS));
        }

        /**
         * Sets metering areas. See {@link #getMeteringAreas()} for
         * documentation.
         *
         * @param meteringAreas the metering areas
         * @see #getMeteringAreas()
         */
        public void setMeteringAreas(List<Area> meteringAreas) {
            //set(KEY_METERING_AREAS, meteringAreas);
        }

        /**
         * Gets the maximum number of detected faces supported. This is the
         * maximum length of the list returned from {@link FaceDetectionListener}.
         * If the return value is 0, face detection of the specified type is not
         * supported.
         *
         * @return the maximum number of detected face supported by the camera.
         * @see #startFaceDetection()
         */
        public int getMaxNumDetectedFaces() {
            return getInt(KEY_MAX_NUM_DETECTED_FACES_HW, 0);
        }

        /**
         * Sets recording mode hint. This tells the camera that the intent of
         * the application is to record videos {@link
         * android.media.MediaRecorder#start()}, not to take still pictures
         * {@link #takePicture(Camera.ShutterCallback, Camera.PictureCallback,
         * Camera.PictureCallback, Camera.PictureCallback)}. Using this hint can
         * allow MediaRecorder.start() to start faster or with fewer glitches on
         * output. This should be called before starting preview for the best
         * result, but can be changed while the preview is active. The default
         * value is false.
         *
         * The app can still call takePicture() when the hint is true or call
         * MediaRecorder.start() when the hint is false. But the performance may
         * be worse.
         *
         * @param hint true if the apps intend to record videos using
         *             {@link android.media.MediaRecorder}.
         */
        public void setRecordingHint(boolean hint) {
            set(KEY_RECORDING_HINT, hint ? TRUE : FALSE);
        }

        /**
         * <p>Returns true if video snapshot is supported. That is, applications
         * can call {@link #takePicture(Camera.ShutterCallback,
         * Camera.PictureCallback, Camera.PictureCallback,
         * Camera.PictureCallback)} during recording. Applications do not need
         * to call {@link #startPreview()} after taking a picture. The preview
         * will be still active. Other than that, taking a picture during
         * recording is identical to taking a picture normally. All settings and
         * methods related to takePicture work identically. Ex:
         * {@link #getPictureSize()}, {@link #getSupportedPictureSizes()},
         * {@link #setJpegQuality(int)}, {@link #setRotation(int)}, and etc. The
         * picture will have an EXIF header. {@link #FLASH_MODE_AUTO} and
         * {@link #FLASH_MODE_ON} also still work, but the video will record the
         * flash.</p>
         *
         * <p>Applications can set shutter callback as null to avoid the shutter
         * sound. It is also recommended to set raw picture and post view
         * callbacks to null to avoid the interrupt of preview display.</p>
         *
         * <p>Field-of-view of the recorded video may be different from that of the
         * captured pictures. The maximum size of a video snapshot may be
         * smaller than that for regular still captures. If the current picture
         * size is set higher than can be supported by video snapshot, the
         * picture will be captured at the maximum supported size instead.</p>
         *
         * @return true if video snapshot is supported.
         */
        public boolean isVideoSnapshotSupported() {
            String str = get(KEY_VIDEO_SNAPSHOT_SUPPORTED);
            return TRUE.equals(str);
        }

        /**
         * <p>Enables and disables video stabilization. Use
         * {@link #isVideoStabilizationSupported} to determine if calling this
         * method is valid.</p>
         *
         * <p>Video stabilization reduces the shaking due to the motion of the
         * camera in both the preview stream and in recorded videos, including
         * data received from the preview callback. It does not reduce motion
         * blur in images captured with
         * {@link Camera#takePicture takePicture}.</p>
         *
         * <p>Video stabilization can be enabled and disabled while preview or
         * recording is active, but toggling it may cause a jump in the video
         * stream that may be undesirable in a recorded video.</p>
         *
         * @param toggle Set to true to enable video stabilization, and false to
         * disable video stabilization.
         * @see #isVideoStabilizationSupported()
         * @see #getVideoStabilization()
         */
        public void setVideoStabilization(boolean toggle) {
            set(KEY_VIDEO_STABILIZATION, toggle ? TRUE : FALSE);
        }

        /**
         * Get the current state of video stabilization. See
         * {@link #setVideoStabilization} for details of video stabilization.
         *
         * @return true if video stabilization is enabled
         * @see #isVideoStabilizationSupported()
         * @see #setVideoStabilization(boolean)
         */
        public boolean getVideoStabilization() {
            String str = get(KEY_VIDEO_STABILIZATION);
            return TRUE.equals(str);
        }

        /**
         * Returns true if video stabilization is supported. See
         * {@link #setVideoStabilization} for details of video stabilization.
         *
         * @return true if video stabilization is supported
         * @see #setVideoStabilization(boolean)
         * @see #getVideoStabilization()
         */
        public boolean isVideoStabilizationSupported() {
            String str = get(KEY_VIDEO_STABILIZATION_SUPPORTED);
            return TRUE.equals(str);
        }

        // Splits a comma delimited string to an ArrayList of String.
        // Return null if the passing string is null or the size is 0.
        private ArrayList<String> split(String str) {
            if (str == null) return null;

            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
            //splitter.setString(str);
            ArrayList<String> substrings = new ArrayList<String>();
            //for (String s : splitter) {
            for (String s : str.split(",")) {
                substrings.add(s);
            }
            return substrings;
        }

        // Splits a comma delimited string to an ArrayList of Integer.
        // Return null if the passing string is null or the size is 0.
        private ArrayList<Integer> splitInt(String str) {
            if (str == null) return null;

            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
            //splitter.setString(str);
            ArrayList<Integer> substrings = new ArrayList<Integer>();
            //for (String s : splitter) {
            for (String s : str.split(",")) {
                substrings.add(Integer.parseInt(s));
            }
            if (substrings.size() == 0) return null;
            return substrings;
        }

        private void splitInt(String str, int[] output) {
            if (str == null) return;

            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
            //splitter.setString(str);
            int index = 0;
            //for (String s : splitter) {
            for (String s : str.split(",")) {
                output[index++] = Integer.parseInt(s);
            }
        }

        // Splits a comma delimited string to an ArrayList of Float.
        private void splitFloat(String str, float[] output) {
            if (str == null) return;

            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
            //splitter.setString(str);
            int index = 0;
            //for (String s : splitter) {
            for (String s : str.split(",")) {
                output[index++] = Float.parseFloat(s);
            }
        }

        // Returns the value of a float parameter.
        private float getFloat(String key, float defaultValue) {
            try {
                return Float.parseFloat(mMap.get(key));
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }

        // Returns the value of a integer parameter.
        private int getInt(String key, int defaultValue) {
            try {
                return Integer.parseInt(mMap.get(key));
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }

        // Splits a comma delimited string to an ArrayList of Size.
        // Return null if the passing string is null or the size is 0.
        private ArrayList<Size> splitSize(String str) {
            if (str == null) return null;

            //TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
            //splitter.setString(str);
            ArrayList<Size> sizeList = new ArrayList<Size>();
            //for (String s : splitter) {
            for (String s : str.split(",")) {
                Size size = strToSize(s);
                if (size != null) sizeList.add(size);
            }
            if (sizeList.size() == 0) return null;
            return sizeList;
        }

        // Parses a string (ex: "480x320") to Size object.
        // Return null if the passing string is null.
        private Size strToSize(String str) {
            if (str == null) return null;

            int pos = str.indexOf('x');
            if (pos != -1) {
                String width = str.substring(0, pos);
                String height = str.substring(pos + 1);
                return new Size(Integer.parseInt(width),
                                Integer.parseInt(height));
            }
            //Log.e(TAG, "Invalid size parameter string=" + str);
            return null;
        }

        // Splits a comma delimited string to an ArrayList of int array.
        // Example string: "(10000,26623),(10000,30000)". Return null if the
        // passing string is null or the size is 0.
        private ArrayList<int[]> splitRange(String str) {
            if (str == null || str.charAt(0) != '('
                    || str.charAt(str.length() - 1) != ')') {
                //Log.e(TAG, "Invalid range list string=" + str);
                return null;
            }

            ArrayList<int[]> rangeList = new ArrayList<int[]>();
            int endIndex, fromIndex = 1;
            do {
                int[] range = new int[2];
                endIndex = str.indexOf("),(", fromIndex);
                if (endIndex == -1) endIndex = str.length() - 1;
                splitInt(str.substring(fromIndex, endIndex), range);
                rangeList.add(range);
                fromIndex = endIndex + 3;
            } while (endIndex != str.length() - 1);

            if (rangeList.size() == 0) return null;
            return rangeList;
        }

        // Splits a comma delimited string to an ArrayList of Area objects.
        // Example string: "(-10,-10,0,0,300),(0,0,10,10,700)". Return null if
        // the passing string is null or the size is 0 or (0,0,0,0,0).
        private ArrayList<Area> splitArea(String str) {
            if (str == null || str.charAt(0) != '('
                    || str.charAt(str.length() - 1) != ')') {
                //Log.e(TAG, "Invalid area string=" + str);
                return null;
            }

            ArrayList<Area> result = new ArrayList<Area>();
            int endIndex, fromIndex = 1;
            int[] array = new int[5];
            do {
                endIndex = str.indexOf("),(", fromIndex);
                if (endIndex == -1) endIndex = str.length() - 1;
                splitInt(str.substring(fromIndex, endIndex), array);
                Rect rect = new Rect(array[0], array[1], array[2], array[3]);
                result.add(new Area(rect, array[4]));
                fromIndex = endIndex + 3;
            } while (endIndex != str.length() - 1);

            if (result.size() == 0) return null;

            if (result.size() == 1) {
                Area area = result.get(0);
                Rect rect = area.rect;
                if (rect.left == 0 && rect.top == 0 && rect.right == 0
                        && rect.bottom == 0 && area.weight == 0) {
                    return null;
                }
            }

            return result;
        }

        private boolean same(String s1, String s2) {
            if (s1 == null && s2 == null) return true;
            if (s1 != null && s1.equals(s2)) return true;
            return false;
        }
    };

}
