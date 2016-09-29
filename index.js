import React, { Component, PropTypes } from "react"
import { requireNativeComponent, View } from "react-native";

const RNMultiViewPager = requireNativeComponent("RNMultiViewPager", MultiViewPager);

class MultiViewPager extends Component {

    static propTypes = {

    };

    render() {
        console.log("RNMultiViewPager: ", RNMultiViewPager);
        return (
            <RNMultiViewPager/>
        );
    }
}

export default MultiViewPager;
