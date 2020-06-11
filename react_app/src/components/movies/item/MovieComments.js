import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React, {useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import {movieApi} from "../../../utils/api/movie.api";
import {AccountCircle} from "@material-ui/icons";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import * as colors from "@material-ui/core/colors";


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
                        <ListItemIcon>
                            <AccountCircle fontSize="large" style={{ color: colors.common.white }} />
                        </ListItemIcon>

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