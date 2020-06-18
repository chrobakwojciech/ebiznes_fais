import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import React, {useEffect, useState} from "react";
import {movieApi} from "../../../utils/api/movie.api";
import {NavLink} from "react-router-dom";

export default function MovieDirectors({movieId}) {
    const [directors, setDirectors] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            let directors = await movieApi.getMovieDirectors(movieId);
            setDirectors(directors);
        };
        fetchData();
    }, [movieId]);
    return (
        <>
            <h3>Reżyseria</h3>
            {directors.map(director => (
                <ListItem button component={NavLink} exact={true} to={`/rezyser/${director.id}`}>
                    <ListItemAvatar>
                        <Avatar
                            alt={`${director.firstName} ${director.lastName}`}
                            src={director.img}
                        />
                    </ListItemAvatar>
                    <ListItemText id={director.id} primary={`${director.firstName} ${director.lastName}`} />
                </ListItem>
            ))}
        </>
    )

}
