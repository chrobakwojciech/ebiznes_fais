import React, {useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "./MovieGridItem";
import {useParams} from "react-router-dom";
import Button from "@material-ui/core/Button";
import * as _ from 'lodash';
import ButtonGroup from "@material-ui/core/ButtonGroup";
import {genreApi} from "../../../utils/api/genre.api";
import {movieApi} from "../../../utils/api/movie.api";

export default function MovieGrid() {
    const [movies, setMovies] = useState([]);
    const [gridTitle, setGridTitle] = useState(null);
    const urlParams = useParams();

    useEffect(() => {
        const fetchData = async () => {
            const genres = await genreApi.getAll();
            const genre = genres.find(genre => genre.name.toLowerCase() === urlParams.genreName);

            let movies = [];

            if (genre) {
                setGridTitle(genre.name);
                movies = await movieApi.getForGenre(genre.id);
            } else {
                setGridTitle(null);
                movies = await movieApi.getAll();
            }

            await movieApi.addRatings(movies);
            setMovies(movies);
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
            <h2>{gridTitle}</h2>
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