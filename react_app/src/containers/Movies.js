import React, {useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "../components/movies/MovieGridItem";
import API from "../utils/API";
import {useParams} from "react-router-dom";

export default function Movies(props) {
    const [movies, setMovies] = useState([]);
    const [genreName, setGenreName] = useState(null);
    const urlParams = useParams();

    useEffect(() => {
        const fetchData = async () => {
            const genres = await API.get('/genres')
            const genre = genres.data.find(genre => genre.name.toLowerCase() === urlParams.genreName)

            let url = '/movies'
            if (genre) {
                setGenreName(genre.name);
                url = `/genres/${genre.id}/movies`
            }
            const result = await API.get(url);
            setMovies(result.data);
        };

        fetchData();
    }, []);

    return (
        <Box m={2}>
            <h2>{genreName}</h2>
            <Grid container spacing={5}>
                {movies.map(movie => (
                    <MovieGridItem movie={movie}/>
                ))}
            </Grid>
        </Box>
    );
}