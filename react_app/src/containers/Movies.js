import React, { useState, useEffect } from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "../components/movies/MovieGridItem";
import API from "../utils/API";

export default function Movies() {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await API.get("/movies");
            const temp = [...result.data, ...result.data];
            setMovies(temp);
        };

        fetchData();
    }, []);

    return (
        <Box m={2}>
            <Grid container spacing={5}>
                {movies.map(movie => (
                    <MovieGridItem movie={movie}/>
                ))}
            </Grid>
        </Box>
    );
}