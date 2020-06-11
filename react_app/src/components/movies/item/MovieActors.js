import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React, {useEffect, useState} from "react";
import {movieApi} from "../../../utils/api/movie.api";

export default function MovieActors({movieId}) {
    const [actors, setActors] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            let actors = await movieApi.getMovieActors(movieId);
            setActors(actors);
        };
        fetchData();
    }, [movieId]);
    return (
        <>
            <h3>Obsada</h3>
            {actors.map(actor => (
                <ListItem button>
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