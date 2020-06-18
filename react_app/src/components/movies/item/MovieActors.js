import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React, {useEffect, useState} from "react";
import {movieApi} from "../../../utils/api/movie.api";
import {NavLink} from "react-router-dom";

export default function MovieActors({movieId}) {
    const [actors, setActors] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            let _actors = await movieApi.getMovieActors(movieId);
            setActors(_actors);
        };
        fetchData();
    }, [movieId]);
    return (
        <>
            <h3>Obsada</h3>
            {actors.map(actor => (
                <ListItem button component={NavLink} exact={true} to={`/aktor/${actor.id}`}>
                    <ListItemAvatar>
                        <Avatar
                            alt={`${actor.firstName} ${actor.lastName}`}
                            src={actor.img}
                        />
                    </ListItemAvatar>
                    <ListItemText id={actor.id} primary={`${actor.firstName} ${actor.lastName}`} />
                </ListItem>
            ))}
        </>
    )

}
