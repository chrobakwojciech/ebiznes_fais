import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React from "react";
import Rating from "@material-ui/lab/Rating/Rating";
import {AccountCircle} from "@material-ui/icons";
import * as colors from "@material-ui/core/colors";
import ListItemIcon from "@material-ui/core/ListItemIcon/ListItemIcon";

export default function MovieRatings({ratings}) {
    return (
        <>
            <h3>Oceny</h3>
            {ratings.map(rating => (
                <ListItem>
                    <ListItemIcon>
                        <AccountCircle fontSize="large" style={{ color: colors.common.white }} />
                    </ListItemIcon>


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