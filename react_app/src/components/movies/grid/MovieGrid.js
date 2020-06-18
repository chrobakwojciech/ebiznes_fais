import React from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "./MovieGridItem";
import Button from "@material-ui/core/Button";
import * as _ from 'lodash';
import ButtonGroup from "@material-ui/core/ButtonGroup";

export default function MovieGrid({movies, setMovies, title}) {
    const sort = (field, dir) => {
        const sortedFields = field === 'title' ? ['title'] : [field, 'title'];
        const sortedMovies = _.sortBy(movies, sortedFields);
        dir === 'asc' ? setMovies(sortedMovies) : setMovies(_.reverse(sortedMovies))

    };

    return (
        <Box m={2}>
            {title ? <h3>{title}</h3> : null}
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
