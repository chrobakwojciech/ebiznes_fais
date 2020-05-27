import {Grid, Paper} from "@material-ui/core";
import React from "react";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles({
    gridItem: {
      backgroundColor: 'transparent'
    },
    movieImg: {
        width: '100%'
    },
    movieTitle: {
        marginBlockStart: '0.3em',
        marginBlockEnd: '0.1em'
    },
    movieProductionYear: {
        color: '#ccc'
    }
});

export default function MovieGridItem(props) {
    const movie = props.movie;
    const classes = useStyles();

    return (
        <Grid item  xl={2} lg={3} md={4} sm={6} xs={12}>
            <Paper elevation={0} className={classes.gridItem}>
                <img className={classes.movieImg} src={movie.img} alt={movie.title}/>
                <h2 className={classes.movieTitle}>{movie.title}</h2>
                <Typography className={classes.movieProductionYear} variant="subtitle2">{movie.productionYear}</Typography>
            </Paper>

        </Grid>
    )

}