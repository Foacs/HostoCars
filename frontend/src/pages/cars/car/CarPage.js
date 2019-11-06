import {
    Box,
    Button,
    CircularProgress,
    ExpansionPanel,
    ExpansionPanelActions,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Grid,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableRow,
    Typography
} from '@material-ui/core';
import { ErrorOutlineRounded as ErrorIcon, SearchRounded as DisplayIcon, SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';
import { changeCurrentPageAction, changeSelectedMenuIndexAction, editCarAction, getCarsAction } from 'actions';
import { CertificateModal, EditCarModal } from 'modals';
import PropTypes from 'prop-types';
import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { CarPropType, DefaultCarPicture, formatDateLabel } from 'resources';

import './CarPage.scss';

class CarPage extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            isCertificateModalOpen: false,
            isEditModalOpen: false
        };

        this.updateComponent = this.updateComponent.bind(this);
        this.onOpenCertificateModal = this.onOpenCertificateModal.bind(this);
        this.onCloseCertificateModal = this.onCloseCertificateModal.bind(this);
        this.onOpenEditCarModal = this.onOpenEditCarModal.bind(this);
        this.onValidateEditCarModal = this.onValidateEditCarModal.bind(this);
        this.onCloseEditCarModal = this.onCloseEditCarModal.bind(this);
    }

    componentDidMount() {
        const { changeSelectedMenuIndex } = this.props;

        changeSelectedMenuIndex(0);

        this.updateComponent();
    }

    componentDidUpdate() {
        this.updateComponent();
    }

    updateComponent() {
        const { cars, changeCurrentPage, getCars, isGetInError, isGetInProgress, match: { params: { id } } } = this.props;

        let content = '';
        if (isGetInProgress) {
            content = <CircularProgress size={20} thickness={4} />;
        } else if (isGetInError) {
            content = <ErrorIcon />;
        } else {
            content = <SmileyIcon />;
        }

        if (cars && 0 < cars.length) {
            const car = cars.find(car => Number(id) === car.id);

            if (car) {
                this.setState({ car });
                content = car.registration;
            } else {
                getCars();

                changeCurrentPage(<SmileyIcon />, [ {
                    label: 'Voitures',
                    link: '/cars'
                } ]);
            }
        } else {
            getCars();
        }

        changeCurrentPage(content, [ {
            label: 'Voitures',
            link: '/cars'
        } ]);
    }

    onOpenCertificateModal() {
        this.setState({ isCertificateModalOpen: true });
    };

    onCloseCertificateModal() {
        this.setState({ isCertificateModalOpen: false });
    };

    onOpenEditCarModal() {
        this.setState({ isEditModalOpen: true });
    };

    onValidateEditCarModal(car) {
        const { editCar } = this.props;

        editCar(car);
    }

    onCloseEditCarModal() {
        this.setState({ isEditModalOpen: false });
    };

    render() {
        const { cars } = this.props;
        const { car, isCertificateModalOpen, isEditModalOpen } = this.state;

        let content;
        if (car) {
            const picture = car.picture ? <img alt={`Car n°${car.id}`} className='PicturePanel-Picture'
                                               src={`data:image/jpeg;base64,${car.picture}`} /> : <DefaultCarPicture
                className='PicturePanel-Picture PicturePanel-Picture_default' />;

            const certificateButton = <IconButton className="InfoPanel-Content-Table-Body-Row-Cell-Button" onClick={this.onOpenCertificateModal}>
                <DisplayIcon className="InfoPanel-Content-Table-Body-Row-Cell-Button-Icon" />
            </IconButton>;

            const commentsSection = <Fragment><Grid item>
                <Typography className='InfoPanel-Content-CommentsSubtitle' variant='subtitle1'>
                    Commentaires
                </Typography>
            </Grid>

                <Grid item>
                    <Typography className='InfoPanel-Content-Comments' variant='body2'>
                        {car.comments}
                    </Typography>
                </Grid>
            </Fragment>;

            content = <Fragment>
                <Grid container spacing={4}>
                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <ExpansionPanel className='InfoPanel' defaultExpanded>
                                <ExpansionPanelSummary className='InfoPanel-Header'>
                                    <Typography className='InfoPanel-Header-Title' color='primary' variant='h6'>
                                        Informations
                                    </Typography>
                                </ExpansionPanelSummary>

                                <ExpansionPanelDetails className='InfoPanel-Content'>
                                    <Grid container direction='column' spacing={2}>
                                        <Grid item>
                                            <Table className='InfoPanel-Content-Table'>
                                                <TableBody className='InfoPanel-Content-Table-Body'>
                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Propriétaire
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {car.owner}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Numéro d'immatriculation
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {car.registration}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Marque
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {car.brand}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Modèle
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {car.model}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Motorisation
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {car.motorization}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Date de mise en circulation
                                                        </TableCell>

                                                        <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            {formatDateLabel(car.releaseDate)}
                                                        </TableCell>
                                                    </TableRow>

                                                    <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                        <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                            Carte grise
                                                        </TableCell>

                                                        <TableCell align='right'
                                                                   className='InfoPanel-Content-Table-Body-Row-Cell InfoPanel-Content-Table-Body-Row-Cell_certificate'>
                                                            {car.certificate && certificateButton}
                                                        </TableCell>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </Grid>

                                        {car.comments && commentsSection}
                                    </Grid>
                                </ExpansionPanelDetails>

                                <ExpansionPanelActions className='InfoPanel-Actions'>
                                    <Button className='InfoPanel-Actions-EditButton' color='primary' onClick={this.onOpenEditCarModal}>
                                        Éditer
                                    </Button>
                                </ExpansionPanelActions>
                            </ExpansionPanel>
                        </Grid>
                    </Grid>

                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <Paper className='PicturePanel'>
                                {picture}
                            </Paper>
                        </Grid>
                    </Grid>
                </Grid>

                <CertificateModal className='CertificateModal' onClose={this.onCloseCertificateModal} open={isCertificateModalOpen}
                                  certificate={car && car.certificate} />

                <EditCarModal car={car} open={isEditModalOpen} onClose={this.onCloseEditCarModal} onValidate={this.onValidateEditCarModal}
                              registrations={cars.filter(currentCar => currentCar.registration !== car.registration)
                                                 .map(currentCar => currentCar.registration)} />
            </Fragment>;
        }

        return <Box id="CarPage">
            {content}
        </Box>;
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    isGetAllInError: state.cars.isGetAllInError,
    isGetAllInProgress: state.cars.isGetAllInProgress
});

const mapDispatchToProps = dispatch => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    editCar: editCarAction,
    getCars: getCarsAction
}, dispatch);

CarPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    editCar: PropTypes.func.isRequired,
    isGetAllInError: PropTypes.bool.isRequired,
    isGetAllInProgress: PropTypes.bool.isRequired,
    match: PropTypes.shape({
        params: PropTypes.shape({
            id: PropTypes.string.isRequired
        }).isRequired
    }).isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarPage);
