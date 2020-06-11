import React, {useContext, useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "./../movies/grid/MovieGridItem";
import Button from "@material-ui/core/Button";
import * as _ from 'lodash';
import ButtonGroup from "@material-ui/core/ButtonGroup";
import {movieApi} from "./../../utils/api/movie.api";
import {UserContext} from "./../../context/UserContext";
import LibraryItem from "./LibraryItem";

export default function UserLibrary() {
    const [movies, setMovies] = useState([]);
    const {userCtx} = useContext(UserContext);

    useEffect(() => {
        const fetchData = async () => {
            let movies = await movieApi.getForUser();
            await movieApi.addRatings(movies);
            if (userCtx.user) {
                await movieApi.addUserInfo(movies)
            }
            setMovies(movies);
        };

        fetchData();
    }, []);


    return (
        <Box m={2}>
            <h2>Moja biblioteka</h2>
            <Grid container spacing={5}>
                {movies.map(movie => (
                    <LibraryItem key={movie.id} movie={movie}/>
                ))}
            </Grid>
        </Box>
    );
}