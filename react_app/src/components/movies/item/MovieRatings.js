import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React from "react";
import Rating from "@material-ui/lab/Rating/Rating";

export default function MovieRatings({ratings}) {
    return (
        <>
            <h3>Oceny</h3>
            {ratings.map(rating => (
                <ListItem>
                    <ListItemAvatar>
                        <Avatar
                            alt={`${rating.user.firstName} ${rating.user.lastName}`}
                            src={'http://via.placeholder.com/100x100'}
                        />
                    </ListItemAvatar>


                    <ListItemText
                        id={rating.id}
                        primary={`${rating.user.firstName} ${rating.user.lastName}`}
                        secondary={
                            <React.Fragment>
                                <Rating value={rating.value} max={10} size="small" readOnly/>
                            </React.Fragment>
                        }
                    />
                </ListItem>
            ))}
        </>
    )

}