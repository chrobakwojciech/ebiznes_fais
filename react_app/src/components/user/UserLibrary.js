import React, {useContext, useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import {movieApi} from "./../../utils/api/movie.api";
import {UserContext} from "../../context/userContext/UserContext";
import LibraryItem from "./LibraryItem";

export default function UserLibrary() {
    const [movies, setMovies] = useState([]);
    const {userCtx} = useContext(UserContext);

    useEffect(() => {
        const fetchData = async () => {
            let _movies = await movieApi.getForUser();
            await movieApi.addRatings(_movies);
            if (userCtx.user) {
                await movieApi.addUserInfo(_movies)
            }
            setMovies(_movies);
        };

        fetchData();
    }, [userCtx.user]);


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
