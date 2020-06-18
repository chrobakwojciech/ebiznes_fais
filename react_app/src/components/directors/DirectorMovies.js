import React, {useContext, useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {directorApi} from "../../utils/api/director.api";
import {movieApi} from "../../utils/api/movie.api";
import {UserContext} from "../../context/userContext/UserContext";
import MovieGrid from "../movies/grid/MovieGrid";

export default function DirectorMovies() {
    const [movies, setMovies] = useState([]);
    const [director, setDirector] = useState({ firstName: '', lastName: ''});
    const urlParams = useParams();
    let history = useHistory();

    useEffect(() => {
        const fetchData = async () => {
            const directorId = urlParams.directorId;
            const _director = await directorApi.get(directorId);

            if (!_director) {
                history.push('/')
            } else {
                let _movies = await movieApi.getForDirector(directorId);
                setDirector(_director);

                setMovies(_movies);
            }
        };

        fetchData();
    }, [urlParams.genreName]);


    return (
        <MovieGrid movies={movies} setMovies={setMovies} title={`Filmy wyreżyserowane przez: ${director.firstName} ${director.lastName}`}/>
    );
}
