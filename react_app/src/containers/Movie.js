import React, {useEffect, useState} from 'react';
import API from "../utils/API";
import {useParams} from "react-router-dom";

export default function Movie(props) {
    const [movie, setMovie] = useState({});
    const [actors, setActors] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [genres, setGenres] = useState([]);
    const [comments, setComments] = useState([]);
    const [ratings, setRatings] = useState([]);

    const urlParams = useParams();

    useEffect(() => {
        const fetchData = async () => {
            const url = `/movies/${urlParams.movieId}`
            let res = await API.get(url)
            setMovie(res.data);

            res = await API.get(`${url}/actors`)
            setActors(res.data);
        };

        fetchData();
    }, [urlParams.movieId]);

    console.log(actors)

    return (
        <div>
            <h1>{movie.title}</h1>
            <img width="400px" src={movie.img}/>
            {actors.map(actor => (
                <p>{actor.firstName} {actor.lastName}</p>
            ))}
        </div>
    );
}