import { useEffect, useState, type ChangeEvent } from "react";
import { Dropdown, Form } from "react-bootstrap";

export interface AutocompleteData {
    value : string;
    textContent : string;
}

interface AppAutocompleteInputProps {
    controlId : string;
    className? : string;
    placeholder? : string;
    label : string;
    initialValue? : string;
    onSelect( dt : AutocompleteData ) : void;
    dados: AutocompleteData[];
}

function AppAutocompleteInput( {
    controlId, 
    className,
    placeholder,
    label,
    initialValue,
    onSelect,
    dados,
} : AppAutocompleteInputProps ) {

    const [value, setValue] = useState<string>( '' );
    const [autocompleteVisible, setAutocompleteVisible] = useState<boolean>( false );
    const [dataItems, setDataItems] = useState<AutocompleteData[]>( [] );

    useEffect( () => {
        if ( initialValue !== null && initialValue !== undefined ) {
            setValue( initialValue );
            refreshDataItems( initialValue );
        }
    }, [] );

    const handleOnChange = async ( e : ChangeEvent<HTMLInputElement>) => {        
        refreshDataItems( e.target.value );
        setValue( e.target.value );
        setAutocompleteVisible( true );
    };

    const handleOnClick = async ( value : string, textContent : string ) => {
        setValue( textContent );
        onSelect( { value : value, textContent: textContent } );
        setAutocompleteVisible( false );
    };

    const refreshDataItems = async ( valueContent : string ) => {
        const items : AutocompleteData[] = [];
        for( let i = 0; i < dados.length; i++ ) {
            if ( dados[ i ].textContent.toLowerCase().includes( valueContent.toLowerCase() ) === true )
                items.push( dados[ i ] );
        }

        setDataItems( items );
    }

    return (
        <Dropdown>
            <Form.Group controlId={controlId} className={className}>
                <Form.Label>{label}</Form.Label>
                <Form.Control type="text" 
                    placeholder={placeholder}
                    value={value} 
                    onChange={handleOnChange} />            
            </Form.Group>
            <Dropdown.Menu show={autocompleteVisible}>
                {dataItems.map( (dado, index) => 
                    <Dropdown.Item key={index} eventKey={dado.value} 
                            onClick={() => handleOnClick( dado.value, dado.textContent )}>
                        {dado.textContent}
                    </Dropdown.Item>
                )}
            </Dropdown.Menu>
        </Dropdown>
    )
}

export default AppAutocompleteInput;