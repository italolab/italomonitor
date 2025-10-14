import { useEffect, useState } from "react";
import { Pagination } from "react-bootstrap";

interface AppPaginationProps {
    numberOfItemsByPage: number;
    numberOfPagesByGroup: number;
    dataList: unknown[];
    onChangePageDataList( pageDataList: unknown[] ): void;
}

function AppPagination( { 
    dataList, 
    numberOfItemsByPage,
    numberOfPagesByGroup,
    onChangePageDataList 
} : AppPaginationProps ) {

    const [ pages, setPages ] = useState<{ pageNumber:number, active:boolean }[]>( [] );
    const [ firstPageNumber, setFirstPageNumber ] = useState<number>( 0 );

    useEffect( () => {
        const firstPGNumber = 1;

        setFirstPageNumber( firstPGNumber );

        updatePages( firstPGNumber );
        updatePageDataList( firstPGNumber );
    }, [dataList] );

    const handleFirst = async () => {
        setFirstPageNumber( 1 );
        updatePages( 1 );
        updatePageDataList( 1 );
    };

    const handleLast = async () => {
        const { numberOfGroups } = calcInfos();     
        
        const firstPGNumber = ( (numberOfGroups-1) * numberOfPagesByGroup ) + 1;

        setFirstPageNumber( firstPGNumber );
        updatePages( firstPGNumber );
        updatePageDataList( firstPGNumber );
    };

    const handlePrev = async () => {
        let firstPGNumber = firstPageNumber;
        if ( firstPGNumber > numberOfPagesByGroup )
            firstPGNumber -= numberOfPagesByGroup;
        
        setFirstPageNumber( firstPGNumber );
        updatePages( firstPGNumber );
        updatePageDataList( firstPGNumber );
    };

    const handleNext = async () => {
        const { numberOfGroups } = calcInfos();        

        const lastGroupFirstPGNumber = ( (numberOfGroups-1) * numberOfPagesByGroup ) + 1;

        let firstPGNumber = firstPageNumber;
        if ( firstPGNumber < lastGroupFirstPGNumber )
            firstPGNumber += numberOfPagesByGroup;
        
        setFirstPageNumber( firstPGNumber );
        updatePages( firstPGNumber );
        updatePageDataList( firstPGNumber );
    };

    const handlePage = async ( i : number ) => {
        for( let j = 0; j < pages.length; j++ )
            pages[ j ].active = ( pages[ j ].pageNumber == i );        

        setPages( pages );
        updatePageDataList( i );
    };

    const updatePages = ( firstPage : number ) => {
        const { groupNumberOfPages } = calcInfos();        

        const lastPage = firstPage + groupNumberOfPages - 1;

        const pagesList : { pageNumber: number, active : boolean }[] = [];
        for( let j = firstPage; j <= lastPage; j++ )
            pagesList.push( { pageNumber: j, active : j == firstPage } );

        setPages( pagesList );
    };

    const calcInfos = () => {
        let totalNumberOfPages : number = Math.floor( dataList.length / numberOfItemsByPage );
        if ( dataList.length % numberOfItemsByPage > 0 )
            totalNumberOfPages++;
        
        let groupNumberOfPages;
        if ( totalNumberOfPages > numberOfPagesByGroup )
            groupNumberOfPages = numberOfPagesByGroup;
        else groupNumberOfPages = totalNumberOfPages;

        let numberOfGroups = Math.floor( totalNumberOfPages / numberOfPagesByGroup );
        if ( totalNumberOfPages % numberOfPagesByGroup > 0 )
            numberOfGroups++;

        return { totalNumberOfPages, groupNumberOfPages, numberOfGroups };
    }

    const updatePageDataList = ( i : number ) => {
        const firstItemIndex  = (i-1) * numberOfItemsByPage;
        
        let lastItemIndex = firstItemIndex + numberOfItemsByPage - 1;
        if ( lastItemIndex > dataList.length-1 )
            lastItemIndex = dataList.length-1;

        const items : unknown[] = [];
        for( let j = firstItemIndex; j <= lastItemIndex; j++ )
            items.push( dataList[ j ] );

        onChangePageDataList( items );
    };

    return (
        <Pagination>
            <Pagination.First onClick={handleFirst} />
            <Pagination.Prev onClick={handlePrev} />
            { pages.map( ( page, index ) =>
                <Pagination.Item active={page.active} key={index} onClick={() => handlePage( page.pageNumber )}>
                    {page.pageNumber}                    
                </Pagination.Item>
            ) }
            <Pagination.Next onClick={handleNext} />
            <Pagination.Last onClick={handleLast} />
        </Pagination>
    )
}

export default AppPagination;