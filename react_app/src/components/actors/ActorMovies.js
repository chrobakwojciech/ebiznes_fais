import React, {useContext, useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "./../movies/grid/MovieGridItem";
import {useHistory, useParams} from "react-router-dom";
import Button from "@material-ui/core/Button";
import * as _ from 'lodash';
import ButtonGroup from "@material-ui/core/ButtonGroup";
import {actorApi} from "../../utils/api/actor.api";
import {movieApi} from "../../utils/api/movie.api";
import {UserContext} from "../../context/UserContext";

export default function ActorMovies() {
    const [movies, setMovies] = useState([]);
    const [actor, setActor] = useState({ firstName: '', lastName: ''});
    const urlParams = useParams();
    const {userCtx} = useContext(UserContext);
    let history = useHistory();

    useEffect(() => {
        const fetchData = async () => {
            const actorId = urlParams.actorId;
            const actor = await actorApi.get(actorId);

            if (!actor) {
                history.push('/')
            } else {
                let movies = await movieApi.getForActor(actorId)
                setActor(actor);

                await movieApi.addRatings(movies);
                if (userCtx.user) {
                    await movieApi.addUserInfo(movies)
                }
                setMovies(movies);
            }
        };

        fetchData();
    }, [urlParams.genreName]);

    const sort = (field, dir) => {
        const sortedFields = field === 'title' ? ['title'] : [field, 'title'];
        const sortedMovies = _.sortBy(movies, sortedFields);
        dir === 'asc' ? setMovies(sortedMovies) : setMovies(_.reverse(sortedMovies))

    };

    return (
        <Box m={2}>
            <h2>Filmy z udziałem: {actor.firstName} {actor.lastName}</h2>
            <Box mb={2}>
                <ButtonGroup aria-label="outlined button group">
                    <Button variant="outlined" onClick={() => sort('rating')}>Najlepiej oceniane</Button>
                    <Button onClick={() => sort('productionYear')}>Najnowsze</Button>
                    <Button onClick={() => sort('title', 'asc')}>Alfabetycznie</Button>
                </ButtonGroup>
            </Box>
            <Grid container spacing={5}>
                {movies.map(movie => (
                    <MovieGridItem key={movie.id} movie={movie}/>
                ))}
            </Grid>
        </Box>
    );
}