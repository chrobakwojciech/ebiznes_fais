import {createMuiTheme} from '@material-ui/core/styles';

const theme = createMuiTheme({
    overrides: {
      MuiListItem: {
          root: {
              '&.active': {
                  backgroundColor: '#222b31'
              },
              '&.active:hover': {
                  backgroundColor: '#222b31'
              }
          }
      }
    },
    typography: {
        fontFamily: [
            'Catamaran'
        ],
        h5: {
            fontWeight: "bold"
        }
    },
    palette: {
        primary: {
            main: '#ff424f',
        },
        secondary: {
            main: '#ffb708',
        },
        text: {
            primary: '#fff'
        },
        background: {
            paper: '#191c1f',
            default: '#222b31'
        },
    },
});

// theme.shadows = [];
export default theme;