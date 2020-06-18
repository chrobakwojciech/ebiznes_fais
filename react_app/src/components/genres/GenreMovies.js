import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {genreApi} from "../../utils/api/genre.api";
import {movieApi} from "../../utils/api/movie.api";
import MovieGrid from "../movies/grid/MovieGrid";

export default function GenreMovies() {
    const [movies, setMovies] = useState([]);
    const [gridTitle, setGridTitle] = useState(null);
    const urlParams = useParams();
    let history = useHistory();

    useEffect(() => {
        const fetchData = async () => {
            const genres = await genreApi.getAll();
            const _genre = genres.find(genre => genre.name.toLowerCase() === urlParams.genreName);

            if (!_genre) {
                history.push('/');
                return
            }
            let _movies = await movieApi.getForGenre(_genre.id);
            setGridTitle(_genre.name);

            setMovies(_movies);
        };

        fetchData();
    }, [urlParams.genreName]);


    return (
       <MovieGrid movies={movies} setMovies={setMovies} title={gridTitle} />
    );
}
