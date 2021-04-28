package shortvideo.declantea.me.Enum;


public enum VideoStateEnum {
    Pending(VideoStateEnum.Code.Pending),
    Approve(VideoStateEnum.Code.Approve),
    NotApprove(VideoStateEnum.Code.NotApprove),
    Delete(VideoStateEnum.Code.DELETE);

    private final String videoState;

    VideoStateEnum(String videoState) {
        this.videoState = videoState;
    }

    public String getVideoState() {
        return videoState;
    }

    public static class Code {
        public static final String Pending = "VIDEO_PENDING";
        public static final String Approve = "VIDEO_APPROVE";
        public static final String NotApprove = "VIDEO_NOT_APPROVE";
        public static final String DELETE = "VIDEO_DELETE";
    }
}
