import API from "./API";

class CommentApi {
    async getAll() {
        let comments = [];
        try {
            comments = await API.get('/comments');
            comments = comments.data;
        } catch (e) {
            console.log(e);
        }
        return comments;
    }
}

export const commentApi = new CommentApi();