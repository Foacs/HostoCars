import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import {
    CircularProgress, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Fab, Grid, IconButton, Paper, Table, TableBody, TableCell,
    TableRow, Typography
} from '@material-ui/core';
import {
    DeleteOutlineRounded as DeleteIcon, EditRounded as UpdateIcon, ErrorOutlineRounded as ErrorIcon, RefreshRounded as RefreshIcon,
    SearchRounded as DisplayIcon, SentimentDissatisfiedRounded as SmileyIcon
} from '@material-ui/icons';

import {
    deleteCarAction, getCarsAction, updateCarAction, updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction
} from 'actions';
import { BottomBar, ErrorPanel, InterventionPreview, LoadingPanel, Page } from 'components';
import { CertificateModal, DeleteCarModal, UpdateCarModal, UpdateInterventionModal } from 'modals';
import { NotFoundPage } from 'pages';
import { CarPropType, DefaultCarPicture, formatDateLabel } from 'resources';

import './CarPage.scss';

/**
 * The car page component.
 *
 * @param {object[]} cars
 *     The list of all the cars
 * @param {func} deleteCar
 *     The {@link deleteCarAction} action
 * @param {func} getCars
 *     The {@link getCarsAction} action
 * @param {boolean} isInError
 *     If the car loading is in error
 * @param {boolean} isLoading
 *     If the car loading is in progress
 * @param {object} match
 *     The URL parameters
 * @param {func} updateCar
 *     The {@link updateCarAction} action
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateMenuItems
 *     The {@link updateMenuItemsAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class CarPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = {
            expandedInterventionIndex: false,
            isCertificateModalOpen: false,
            isDeleteCarModalOpen: false,
            isUpdateCarModalOpen: false,
            isUpdateInterventionsModalOpen: false,
            isUpdateInterventionsModalValidationActive: false,
            redirect: false,
            updateInterventionsModalCar: undefined
        };

        // Binds the local methods

        // Breadcrumbs functions
        this.updateBreadcrumbs = this.updateBreadcrumbs.bind(this);

        // Certificate modal functions
        this.onCloseCertificateModal = this.onCloseCertificateModal.bind(this);
        this.onOpenCertificateModal = this.onOpenCertificateModal.bind(this);

        // Delete car modal functions
        this.onCloseDeleteCarModal = this.onCloseDeleteCarModal.bind(this);
        this.onOpenDeleteCarModal = this.onOpenDeleteCarModal.bind(this);
        this.onValidateDeleteCarModal = this.onValidateDeleteCarModal.bind(this);

        // Interventions functions
        this.onInterventionPreviewClick = this.onInterventionPreviewClick.bind(this);

        // Update car modal functions
        this.onCloseUpdateCarModal = this.onCloseUpdateCarModal.bind(this);
        this.onOpenUpdateCarModal = this.onOpenUpdateCarModal.bind(this);
        this.onValidateUpdateCarModal = this.onValidateUpdateCarModal.bind(this);

        // Update interventions modal functions
        this.onCloseUpdateInterventionsModal = this.onCloseUpdateInterventionsModal.bind(this);
        this.onEnterUpdateInterventionsModal = this.onEnterUpdateInterventionsModal.bind(this);
        this.onOpenUpdateInterventionsModal = this.onOpenUpdateInterventionsModal.bind(this);
        this.onUpdateInterventionsModalCar = this.onUpdateInterventionsModalCar.bind(this);
        this.onValidateUpdateInterventionsModal = this.onValidateUpdateInterventionsModal.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const {
            getCars,
            updateMenuItems,
            updateSelectedMenuIndex
        } = this.props;

        updateMenuItems([ {
            icon: <DeleteIcon />,
            label: 'Supprimer',
            onClick: this.onOpenDeleteCarModal
        }, {
            icon: <RefreshIcon />,
            label: 'Rafraîchir',
            onClick: getCars
        } ]);
        updateSelectedMenuIndex(0);

        this.updateBreadcrumbs();
    }

    /**
     * Method called when the component did update.
     *
     * @param {object} prevProps
     *     The previous props
     */
    componentDidUpdate(prevProps) {
        this.updateBreadcrumbs();
    }

    /**
     * Handles the certificate modal close action.
     */
    onCloseCertificateModal() {
        this.setState({ isCertificateModalOpen: false });
    }

    /**
     * Handles the 'Delete car' modal close action.
     */
    onCloseDeleteCarModal() {
        this.setState({ isDeleteCarModalOpen: false });
    }

    /**
     * Handles the 'Update car' modal close action.
     */
    onCloseUpdateCarModal() {
        this.setState({ isUpdateCarModalOpen: false });
    }

    /**
     * Handles the 'Update interventions' modal close action.
     */
    onCloseUpdateInterventionsModal() {
        this.setState({
            isUpdateInterventionsModalOpen: false,
            isUpdateInterventionsModalValidationActive: false
        });
    }

    /**
     * Handles the 'Update interventions' modal enter action.
     */
    onEnterUpdateInterventionsModal() {
        const {
            cars,
            match: { params: { id } }
        } = this.props;

        this.setState({
            updateInterventionsModalCar: JSON.parse(JSON.stringify(cars.find(car => Number(id) === car.id)))
        });
    }

    /**
     * Handles an intervention preview panel click action.
     * <br/>
     * This method is used to create an accordion effect on the interventions previews.
     *
     * @param index
     *    The index of the expanded intervention preview panel
     */
    onInterventionPreviewClick(index) {
        this.setState({ expandedInterventionIndex: this.state.expandedInterventionIndex === index ? false : index });
    }

    /**
     * Handles the certificate button click action.
     */
    onOpenCertificateModal() {
        this.setState({ isCertificateModalOpen: true });
    }

    /**
     * Handles the 'Delete car' button click action.
     */
    onOpenDeleteCarModal() {
        this.setState({ isDeleteCarModalOpen: true });
    }

    /**
     * Handles the 'Update car' button click action.
     */
    onOpenUpdateCarModal() {
        this.setState({ isUpdateCarModalOpen: true });
    }

    /**
     * Handles the 'Update interventions' button click action.
     */
    onOpenUpdateInterventionsModal() {
        this.setState({
            isUpdateInterventionsModalOpen: true,
            isUpdateInterventionsModalValidationActive: false
        });
    }

    /**
     * Handles the intervention creation action.
     */
    onUpdateInterventionsModalCar(car) {
        this.setState({ updateInterventionsModalCar: car });
    }

    /**
     * Handles the 'Delete car' modal validate action.
     */
    async onValidateDeleteCarModal() {
        const {
            deleteCar,
            match: { params: { id } }
        } = this.props;

        await deleteCar(Number(id));
        this.setState({ redirect: true });
    }

    /**
     * Handles the 'Update car' modal validate action.
     */
    onValidateUpdateCarModal(car) {
        const { updateCar } = this.props;

        updateCar(car);
    }

    /**
     * Handles the 'Update interventions' modal validate action.
     */
    onValidateUpdateInterventionsModal(isValid, car) {
        if (isValid) {
            this.setState({
                isUpdateInterventionsModalOpen: false,
                isUpdateInterventionsModalValidationActive: false
            });

            const { updateCar } = this.props;
            updateCar(car);
        } else {
            this.setState({ isUpdateInterventionsModalValidationActive: true });
        }
    }

    /**
     * Updates the breadcrumbs.
     */
    updateBreadcrumbs() {
        const {
            cars,
            isInError,
            isLoading,
            match: { params: { id } },
            updateCurrentPage
        } = this.props;

        let content;
        if (isInError) {
            // If the car or the registrations failed to be loaded, displays an error icon
            content = <ErrorIcon />;
        } else if (isLoading) {
            // If the car or the registrations are being loaded, displays a circular progress
            content = <CircularProgress size={20} thickness={4} />;
        } else {
            // If there are cars, tries to get the current one
            const foundCar = cars.find(car => Number(id) === car.id);

            if (foundCar) {
                // If the current car has been found, displays its registration
                content = foundCar.registration;
            } else {
                // If the car has not been found, displays the smiley icon
                content = <SmileyIcon />;
            }
        }

        updateCurrentPage(content, [ {
            label: 'Voitures',
            link: '/cars'
        } ]);
    }

    render() {
        const {
            cars,
            isInError,
            isLoading,
            match: { params: { id } }
        } = this.props;
        const {
            expandedInterventionIndex,
            isCertificateModalOpen,
            isDeleteCarModalOpen,
            isUpdateCarModalOpen,
            isUpdateInterventionsModalOpen,
            isUpdateInterventionsModalValidationActive,
            redirect,
            updateInterventionsModalCar
        } = this.state;

        let content;
        if (redirect) {
            // Redirects to the overview page
            content = <Redirect to='/cars' />;
        } else if (isInError) {
            // If the cars failed to be loaded, displays the error panel
            content = <ErrorPanel />;
        } else if (isLoading) {
            // If the cars are being loaded, displays the loading panel
            content = <LoadingPanel />;
        } else {
            const foundCar = cars.find(car => Number(id) === car.id);

            if (foundCar) {
                // If the car has been found, displays the car content
                const picture = foundCar.picture ? <img alt={`Car n°${foundCar.id}`} className='CarPicture'
                                                        src={`data:image/jpeg;base64,${foundCar.picture}`} /> :
                        <DefaultCarPicture className='CarPicture CarPicture_default' />;

                const certificateButton = <IconButton className='CertificateButton' onClick={this.onOpenCertificateModal}>
                    <DisplayIcon />
                </IconButton>;

                const commentsSection = (<Grid className='CommentsSection' container direction='column'>
                    <Grid item xs={1}>
                        <Typography className='CommentsSubtitle' variant='subtitle1'>Commentaires</Typography>
                    </Grid>

                    <Grid item xs={11}>
                        <Typography className='Comments' variant='body2'>{foundCar.comments}</Typography>
                    </Grid>
                </Grid>);

                const existingRegistrations = cars.map(c => c.registration)
                        .filter(registration => registration !== foundCar.registration);

                const serialNumbers = cars && cars.filter(currentCar => foundCar.serialNumber !== currentCar.serialNumber)
                        .map(currentCar => currentCar.serialNumber);

                const carToUpdate = updateInterventionsModalCar ? updateInterventionsModalCar : JSON.parse(JSON.stringify(foundCar));

                const interventionsPanelContent = (0 === foundCar.interventions.length
                        ? <Typography align='center' className='NoInterventionsLabel' variant='body1'>Aucune intervention à afficher</Typography>
                        : <Grid container>
                            {foundCar.interventions.map((intervention, index) =>
                                    <InterventionPreview intervention={intervention} expanded={expandedInterventionIndex === index} key={index}
                                                         onClick={() => this.onInterventionPreviewClick(index)} />)}
                        </Grid>);

                content = (<>
                    <Grid container spacing={4}>
                        <Grid item xs={4}>
                            <Paper className='PicturePanel'>
                                {picture}

                                <BottomBar />
                            </Paper>

                            <ExpansionPanel className='InfoPanel' expanded>
                                <ExpansionPanelSummary className='InfoPanelHeader'>
                                    <Typography className='InfoPanelTitle' color='primary' variant='h6'>Informations</Typography>

                                    <IconButton className='UpdateCarButton' color='primary' onClick={this.onOpenUpdateCarModal}>
                                        <UpdateIcon />
                                    </IconButton>
                                </ExpansionPanelSummary>

                                <ExpansionPanelDetails>
                                    <Grid container direction='column'>
                                        <Table>
                                            <TableBody>
                                                {foundCar.owner && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Propriétaire</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.owner}</TableCell>
                                                </TableRow>}

                                                {foundCar.brand && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Marque</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.brand}</TableCell>
                                                </TableRow>}

                                                {foundCar.engineCode && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Code moteur</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.engineCode}</TableCell>
                                                </TableRow>}

                                                {foundCar.registration && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Immatriculation</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.registration}</TableCell>
                                                </TableRow>}

                                                {foundCar.model && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Modèle</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.model}</TableCell>
                                                </TableRow>}

                                                {foundCar.releaseDate && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Mise en circulation</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{formatDateLabel(
                                                            foundCar.releaseDate)}</TableCell>
                                                </TableRow>}

                                                {foundCar.serialNumber && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>VIN</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.serialNumber}</TableCell>
                                                </TableRow>}

                                                {foundCar.motorization && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Motorisation</TableCell>
                                                    <TableCell align='right' className='TableRowValue'>{foundCar.motorization}</TableCell>
                                                </TableRow>}

                                                {foundCar.certificate && <TableRow className='TableRow' hover>
                                                    <TableCell className='TableRowLabel'>Carte grise</TableCell>
                                                    <TableCell align='right' className='CertificateCell'>
                                                        {foundCar.certificate && certificateButton}
                                                    </TableCell>
                                                </TableRow>}
                                            </TableBody>
                                        </Table>

                                        {foundCar.comments && commentsSection}
                                    </Grid>
                                </ExpansionPanelDetails>

                                <BottomBar />
                            </ExpansionPanel>
                        </Grid>

                        <Grid item xs={8}>
                            <ExpansionPanel className='InterventionsPanel' expanded>
                                <ExpansionPanelSummary className='InterventionsPanelHeader'>
                                    <Typography className='InterventionsPanelTitle' color='primary' variant='h6'>Interventions</Typography>

                                    <IconButton className='UpdateInterventionsButton' color='primary' onClick={this.onOpenUpdateInterventionsModal}>
                                        <UpdateIcon />
                                    </IconButton>
                                </ExpansionPanelSummary>

                                <ExpansionPanelDetails className='InterventionsPanelContent'>
                                    {interventionsPanelContent}
                                </ExpansionPanelDetails>

                                <BottomBar />
                            </ExpansionPanel>
                        </Grid>
                    </Grid>

                    <Fab className='DeleteCarButton' color='primary' onClick={this.onOpenDeleteCarModal} size="medium" variant='round'>
                        <DeleteIcon className='DeleteCarIcon' />
                    </Fab>

                    {foundCar.certificate && <CertificateModal certificate={foundCar.certificate} onClose={this.onCloseCertificateModal}
                                                               open={isCertificateModalOpen} />}

                    <UpdateCarModal car={foundCar} onClose={this.onCloseUpdateCarModal} open={isUpdateCarModalOpen}
                                    onValidate={this.onValidateUpdateCarModal} registrations={existingRegistrations} serialNumbers={serialNumbers} />

                    <DeleteCarModal onClose={this.onCloseDeleteCarModal} open={isDeleteCarModalOpen} onValidate={this.onValidateDeleteCarModal} />

                    <UpdateInterventionModal car={carToUpdate} isValidationActive={isUpdateInterventionsModalValidationActive}
                                             onClose={this.onCloseUpdateInterventionsModal}
                                             onEnter={this.onEnterUpdateInterventionsModal} open={isUpdateInterventionsModalOpen}
                                             onUpdateCar={this.onUpdateInterventionsModalCar} onValidate={this.onValidateUpdateInterventionsModal} />
                </>);
            } else {
                // If the car has not been found even after loading the cars, displays the 'Not found' content
                content = (<NotFoundPage label='Voiture introuvable' />);
            }
        }

        return (<Page id='CarPage'>
            {content}
        </Page>);
    }
}

const mapStateToProps = (state) => ({
    cars: state.cars.cars,
    isInError: state.cars.isGetInError,
    isLoading: state.cars.isGetInProgress
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    deleteCar: deleteCarAction,
    getCars: getCarsAction,
    updateCar: updateCarAction,
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

CarPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    getCars: PropTypes.func.isRequired,
    deleteCar: PropTypes.func.isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    match: PropTypes.shape({
        params: PropTypes.shape({
            id: PropTypes.string.isRequired
        }).isRequired
    }).isRequired,
    updateCar: PropTypes.func.isRequired,
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarPage);
