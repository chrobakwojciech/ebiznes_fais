import React from 'react';
import Paper from "@material-ui/core/Paper/Paper";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import Box from "@material-ui/core/Box";
import makeStyles from "@material-ui/core/styles/makeStyles";
import TextField from "@material-ui/core/TextField";
import {Link} from "react-router-dom";

const useStyles = makeStyles(theme => ({
    cardHeader: {
        // textAlign: 'center',
        paddingTop: '16px',
        backgroundColor: theme.palette.background.default,
        color: theme.palette.secondary.main
    },
    cardContent: {
        paddingBlockStart: '32px',
        paddingBlockEnd: '32px',
        backgroundColor: theme.palette.text.primary
    },
    cardActions: {
        paddingBlockStart: '16px',
        backgroundColor: theme.palette.background.default,
    },
    form: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formField: {
        marginBlockEnd: '24px'
    },
    formFieldText: {
        color: theme.palette.text.secondary
    },
    smallButton: {
        color: '#ccc'
    }
}));

export default function Login() {
    const classes = useStyles();

    return (
        <Grid item xs={3}>
            <Card elevation={0}>
                <CardHeader className={classes.cardHeader} title="Logowanie"/>
                <CardContent className={classes.cardContent}>
                    <form className={classes.form}>
                        <TextField
                            InputProps={{
                                className: classes.formFieldText
                            }}
                            className={classes.formField}
                            fullWidth
                            required
                            id="email"
                            label="Adres e-mail"
                        />

                        <TextField
                            InputProps={{
                                className: classes.formFieldText
                            }}
                            className={classes.formField}
                            fullWidth
                            id="password"
                            label="Hasło"
                            type="password"
                        />

                    </form>
                </CardContent>
                <CardActions className={classes.cardActions}>
                    <Grid
                        justify="space-between"
                        container
                    >
                        <Grid item><Button variant="outlined" color="secondary">Zaloguj się</Button></Grid>
                        <Grid item><Button className={classes.smallButton} component={Link} to="/rejestracja">Przejdź do rejestracji</Button></Grid>
                    </Grid>
                </CardActions>
            </Card>
        </Grid>
    );
}