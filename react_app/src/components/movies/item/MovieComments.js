import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React, {useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import {movieApi} from "../../../utils/api/movie.api";

export default function MovieComments({movieId}) {
    const [comments, setComments] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            let comments = await movieApi.getMovieComments(movieId);
            setComments(comments);
        };
        fetchData();
    }, [movieId]);
    if (comments.length === 0) {
        return (
            <h3>Brak komentarzy</h3>
        )
    } else {
        return (
            <>
                <h3>Komentarze</h3>
                {comments.map(comment => (
                    <ListItem>
                        <ListItemAvatar>
                            <Avatar
                                alt={`${comment.user.firstName} ${comment.user.lastName}`}
                                src={'http://via.placeholder.com/100x100'}
                            />
                        </ListItemAvatar>


                        <ListItemText
                            id={comment.id}
                            primary={`${comment.user.firstName} ${comment.user.lastName}`}
                            secondary={
                                <React.Fragment>
                                    <Typography
                                        component="span"
                                        variant="body2"
                                        color="textPrimary"
                                    >{comment.content}</Typography>
                                </React.Fragment>
                            }
                        />
                    </ListItem>
                ))}
            </>
        )
    }


}