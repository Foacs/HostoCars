import React, { useState } from 'react';
import PropTypes from 'prop-types';

import {
    Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, Grid, IconButton, InputAdornment, InputLabel,
    OutlinedInput, TextField
} from '@material-ui/core';
import { CancelRounded as CancelIcon, FolderOpenRounded as BrowseIcon, HelpOutlineRounded as HelpIcon } from '@material-ui/icons';
import { DatePicker } from '@material-ui/pickers';

import { BottomBar } from 'components';
import { CarPropType, ENTER_KEY_CODE, ESCAPE_KEY_CODE, extractFileNameFromUrl, formatDateLabel, loadFileAsByteArray } from 'resources';

import './CarForm.scss';

// Defines the certificate field value label when the current car has a certificate
const currentCertificateLabel = 'Carte grise actuelle';

// Defines the certificate field value label when the current car has an image
const currentImageLabel = 'Image actuelle';

// Defines the text to display when the registration is not provided
const registrationRequiredHelperText = 'Veuillez renseigner l\'immatriculation';

// Defines the text to display when the registration already exists
const registrationUniqueHelperText = 'Ce numéro d\'immatriculation existe déjà';

// Defines the text to display when the serial number already exists
const serialNumberUniqueHelperText = 'Ce VIN existe déjà';

// Defines the text to display when the required owner is not provided
const ownerRequiredHelperText = 'Veuillez renseigner le nom du propriétaire';

/**
 * The car form component.
 *
 * @param {object} [car = {}]
 *     The initial car state
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 * @param {string[]} [registrations = []]
 *     The list of forbidden registration numbers
 * @param {string[]} [serialNumbers = []]
 *     The list of forbidden serial numbers
 * @param {string} title
 *     The form title
 * @param {string} validateButtonLabel
 *     The form validate button label
 *
 * @constructor
 */
function CarForm({
    car,
    className,
    onClose,
    onValidate,
    open,
    registrations,
    serialNumbers,
    title,
    validateButtonLabel
}) {
    // Initializes the help flag
    const [ help, setHelp ] = useState(false);

    // Initializes the car fields
    const [ registration, setRegistration ] = useState(car.registration ? car.registration : '');
    const [ serialNumber, setSerialNumber ] = useState(car.serialNumber ? car.serialNumber : '');
    const [ owner, setOwner ] = useState(car.owner ? car.owner : '');
    const [ brand, setBrand ] = useState(car.brand ? car.brand : '');
    const [ model, setModel ] = useState(car.model ? car.model : '');
    const [ motorization, setMotorization ] = useState(car.motorization ? car.motorization : '');
    const [ engineCode, setEngineCode ] = useState(car.engineCode ? car.engineCode : '');
    const [ releaseDate, setReleaseDate ] = useState(car.releaseDate ? car.releaseDate : null);
    const [ certificate, setCertificate ] = useState(car.certificate ? car.certificate : null);
    const [ picture, setPicture ] = useState(car.picture ? car.picture : null);
    const [ comments, setComments ] = useState(car.comments ? car.comments : '');

    // Initializes the labels for the uploaded files
    const [ certificateFileName, setCertificateFileName ] = useState(car.certificate ? currentCertificateLabel : '');
    const [ pictureFileName, setPictureFileName ] = useState(car.picture ? currentImageLabel : '');

    // Initializes the constraints
    const [ registrationRequired, setRegistrationRequired ] = useState(false);
    const [ registrationUnique, setRegistrationUnique ] = useState(false);
    const [ serialNumberUnique, setSerialNumberUnique ] = useState(false);
    const [ ownerRequired, setOwnerRequired ] = useState(false);

    // Defines the adornment of the certificate field depending on the certificate being null or not
    const certificateFieldAdornment = certificate ? (<InputAdornment position='end'>
        <IconButton onClick={() => onClearFieldAction('certificate')}>
            <CancelIcon />
        </IconButton>
    </InputAdornment>) : (<InputAdornment position='end'>
        <IconButton component='label' variant='contained'>
            <BrowseIcon />
            <input accept='image/jpe, image/jpg, image/jpeg, image/gif, image/png, image/bmp,' id='CertificateInput'
                   onChange={e => onFieldValueChanged(e, 'certificate')} type='file' />
        </IconButton>
    </InputAdornment>);

    // Defines the adornment of the picture field depending on the picture being null or not
    const pictureFieldAdornment = picture ? (<InputAdornment position='end'>
        <IconButton onClick={() => onClearFieldAction('picture')}>
            <CancelIcon />
        </IconButton>
    </InputAdornment>) : (<InputAdornment position='end'>
        <IconButton component='label' variant='contained'>
            <BrowseIcon />
            <input accept='image/jpe, image/jpg, image/jpeg, image/gif, image/png, image/bmp,' id='PictureInput'
                   onChange={e => onFieldValueChanged(e, 'picture')} type='file' />
        </IconButton>
    </InputAdornment>);

    /**
     * Clears the form.
     */
    const clearForm = () => {
        setRegistration(car.registration ? car.registration : '');
        setSerialNumber(car.serialNumber ? car.serialNumber : '');
        setOwner(car.owner ? car.owner : '');
        setBrand(car.brand ? car.brand : '');
        setModel(car.model ? car.model : '');
        setMotorization(car.motorization ? car.motorization : '');
        setEngineCode(car.engineCode ? car.engineCode : '');
        setReleaseDate(car.releaseDate ? car.releaseDate : null);
        setCertificate(car.certificate ? car.certificate : null);
        setPicture(car.picture ? car.picture : null);
        setComments(car.comments ? car.comments : '');

        setPictureFileName(car.picture ? currentImageLabel : '');
        setCertificateFileName(car.certificate ? currentCertificateLabel : '');

        setRegistrationRequired(false);
        setRegistrationUnique(false);
        setSerialNumberUnique(false);
        setOwnerRequired(false);
    };

    /**
     * Handles the clear action for the given field.
     *
     * @param {string} field
     *     The field name
     */
    const onClearFieldAction = (field) => {
        switch (field) {
            case 'certificate':
                setCertificate(null);
                setCertificateFileName('');
                break;
            case 'picture':
                setPicture(null);
                setPictureFileName('');
                break;
            default:
                console.error('Unknown field cleared');
        }
    };

    /**
     * Handles the onEnter action.
     */
    const onEnterAction = () => {
        clearForm();
    };

    /**
     * Handles the value changed event for the given field.
     *
     * @param {object} e
     *     The event
     * @param {string} field
     *     The field name
     */
    const onFieldValueChanged = (e, field) => {
        switch (field) {
            case 'registration':
                setRegistration(e.target.value);
                setRegistrationRequired(false);
                setRegistrationUnique(false);
                break;
            case 'serialNumber':
                setSerialNumber(e.target.value);
                setSerialNumberUnique(false);
                break;
            case 'owner':
                setOwner(e.target.value);
                setOwnerRequired(false);
                break;
            case 'brand':
                setBrand(e.target.value);
                break;
            case 'model':
                setModel(e.target.value);
                break;
            case 'motorization':
                setMotorization(e.target.value);
                break;
            case 'engineCode':
                setEngineCode(e.target.value);
                break;
            case 'releaseDate':
                if (null === e) {
                    setReleaseDate(e);
                } else {
                    setReleaseDate(`${e.getFullYear()}-${(e.getMonth() + 1).toLocaleString('fr', {
                        minimumIntegerDigits: 2,
                        useGrouping: false
                    })}`);
                }
                break;
            case 'certificate':
                const certificateDocument = document.getElementById('CertificateInput');

                const certificateUrl = certificateDocument.value;
                setCertificateFileName(extractFileNameFromUrl(certificateUrl));

                const certificateFile = certificateDocument.files[0];
                setCertificate(loadFileAsByteArray(certificateFile));
                break;
            case 'picture':
                const pictureDocument = document.getElementById('PictureInput');

                const pictureUrl = pictureDocument.value;
                setPictureFileName(extractFileNameFromUrl(pictureUrl));

                const pictureFile = pictureDocument.files[0];
                setPicture(loadFileAsByteArray(pictureFile));
                break;
            case 'comments':
                setComments(e.target.value);
                break;
            default:
                console.error('Unknown field updated');
        }
    };

    /**
     * Handles the help button click action.
     */
    const onHelpButtonClick = () => {
        setHelp(!help);
    };

    /**
     * Handles the key pressed action.
     *
     * @param {object} e
     *     The event
     */
    const onKeyPressed = (e) => {
        switch (e.keyCode) {
            case ENTER_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onValidateAction();
                break;
            case ESCAPE_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onClose();
                break;
            default:
                break;
        }
    };

    /**
     * Handles the validation action.
     */
    const onValidateAction = () => {
        const emptyValue = '';

        // Checks the form validation
        if (validateForm()) {
            const validatedCar = {
                ...car,
                registration: emptyValue === registration ? null : registration,
                serialNumber: emptyValue === serialNumber ? null : serialNumber,
                owner: emptyValue === owner ? null : owner,
                brand: emptyValue === brand ? null : brand,
                model: emptyValue === model ? null : model,
                motorization: emptyValue === motorization ? null : motorization,
                engineCode: emptyValue === engineCode ? null : engineCode,
                releaseDate: emptyValue === releaseDate ? null : releaseDate,
                certificate,
                picture,
                comments: emptyValue === comments ? null : comments
            };

            onValidate(validatedCar);
            onClose();
        }
    };

    /**
     * Returns the text field props for the release date picker depending on the current release date value.
     *
     * @param {object} props
     *     The initial props
     *
     * @returns {*} the text field component with the final props
     */
    const releaseDateFieldProps = (props) => <TextField InputProps={{ shrink: null !== releaseDate }} {...props} />;

    /**
     * Validates the form.
     *
     * @returns {boolean} if the form is valid
     */
    const validateForm = () => {
        let isValid = true;

        // Validates the registration field
        if (null === registration || '' === registration) {
            setRegistrationRequired(true);
            isValid = false;
        } else if (registrations.includes(registration)) {
            setRegistrationUnique(true);
            isValid = false;
        }

        // Validates the serial number field
        if (null != serialNumber && '' !== serialNumber && serialNumbers.includes(serialNumber)) {
            setSerialNumberUnique(true);
            isValid = false;
        }

        // Validates the owner field
        if (null == owner || '' === owner) {
            setOwnerRequired(true);
            isValid = false;
        }

        return isValid;
    };

    return (<Dialog className={className} id='CarForm' onClose={onClose} onEnter={onEnterAction} onKeyDown={onKeyPressed} open={open}>
        <DialogTitle className='Title'>
            {title}

            <IconButton className='HelpButton' color='primary' onClick={onHelpButtonClick}>
                <HelpIcon />
            </IconButton>
        </DialogTitle>

        <DialogContent>
            <DialogContentText className={help ? 'Instructions' : 'Instructions_hidden'}>
                <i>
                    {`Veuillez remplir le formulaire ci-dessous puis cliquer sur '${validateButtonLabel}' pour terminer.
                    Les champs annotés du symbole * sont obligatoires.`}
                </i>
            </DialogContentText>

            <Grid alignItems='center' container direction='column' justify='center'>
                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className={`Field ${ownerRequired && 'Field_error'}`}
                                   error={ownerRequired}
                                   fullWidth
                                   helperText={ownerRequired && ownerRequiredHelperText}
                                   label='Propriétaire'
                                   onChange={e => onFieldValueChanged(e, 'owner')}
                                   required
                                   value={owner}
                                   variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <TextField className={`Field ${(registrationRequired || registrationUnique) && 'Field_error'}`}
                                   error={registrationRequired || registrationUnique}
                                   fullWidth
                                   helperText={registrationRequired ? registrationRequiredHelperText : registrationUnique
                                           && registrationUniqueHelperText}
                                   label="Numéro d'immatriculation"
                                   onChange={e => onFieldValueChanged(e, 'registration')}
                                   required
                                   value={registration}
                                   variant='outlined' />
                    </Grid>
                </Grid>

                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className={`Field ${(serialNumberUnique) && 'Field_error'}`}
                                   error={serialNumberUnique}
                                   fullWidth
                                   helperText={serialNumberUnique && serialNumberUniqueHelperText}
                                   label='VIN'
                                   onChange={e => onFieldValueChanged(e, 'serialNumber')}
                                   value={serialNumber}
                                   variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Marque' onChange={e => onFieldValueChanged(e, 'brand')}
                                   value={brand} variant='outlined' />
                    </Grid>
                </Grid>

                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Modèle' onChange={e => onFieldValueChanged(e, 'model')}
                                   value={model} variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Motorisation'
                                   onChange={e => onFieldValueChanged(e, 'motorization')} value={motorization} variant='outlined' />
                    </Grid>
                </Grid>

                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Code moteur'
                                   onChange={e => onFieldValueChanged(e, 'engineCode')} value={engineCode} variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <DatePicker autoOk
                                    cancelLabel='Annuler'
                                    className='Field'
                                    clearable
                                    clearLabel='Supprimer'
                                    disableFuture
                                    disableToolbar
                                    fullWidth
                                    inputVariant='outlined'
                                    label='Date de mise en circulation'
                                    labelFunc={formatDateLabel}
                                    onChange={e => onFieldValueChanged(e, 'releaseDate')}
                                    TextFieldComponent={releaseDateFieldProps}
                                    value={releaseDate}
                                    variant='dialog'
                                    views={[ 'year', 'month' ]} />
                    </Grid>
                </Grid>
            </Grid>

            <FormControl className='Field' fullWidth variant='outlined'>
                <InputLabel htmlFor='CertificateField'>Carte grise</InputLabel>

                <OutlinedInput id='CertificateField' endAdornment={certificateFieldAdornment} labelWidth={80} readOnly value={certificateFileName} />
            </FormControl>

            <FormControl className='Field' fullWidth variant='outlined'>
                <InputLabel htmlFor='PictureField'>Image</InputLabel>

                <OutlinedInput id='PictureField' endAdornment={pictureFieldAdornment} labelWidth={46} readOnly value={pictureFileName} />
            </FormControl>

            <TextField className='Field' fullWidth label='Commentaires' multiline onChange={e => onFieldValueChanged(e, 'comments')}
                       rowsMax={4} value={comments} variant='outlined' />
        </DialogContent>

        <DialogActions>
            <Button className='CancelButton' color='primary' onClick={onClose}>
                Annuler
            </Button>

            <Button autoFocus className='ValidateButton' color='secondary' onClick={onValidateAction}>
                {validateButtonLabel}
            </Button>
        </DialogActions>

        <BottomBar />
    </Dialog>);
}

CarForm.propTypes = {
    car: CarPropType,
    className: PropTypes.string,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    registrations: PropTypes.arrayOf(PropTypes.string),
    serialNumbers: PropTypes.arrayOf(PropTypes.string),
    title: PropTypes.string.isRequired,
    validateButtonLabel: PropTypes.string.isRequired
};

CarForm.defaultProps = {
    car: {},
    className: '',
    registrations: [],
    serialNumbers: []
};

export default CarForm;
