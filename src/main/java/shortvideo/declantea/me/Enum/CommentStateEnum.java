package shortvideo.declantea.me.Enum;


public enum CommentStateEnum {
    Active(CommentStateEnum.Code.Active),
    Delete(CommentStateEnum.Code.Delete);

    private final String commentState;

    CommentStateEnum(String commentState) {
        this.commentState = commentState;
    }

    public String getCommentState() {
        return commentState;
    }

    public static class Code {
        public static final String Active = "COMMEND_ACTIVE";
        public static final String Delete = "COMMEND_DELETE";
    }
}
