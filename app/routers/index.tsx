import {createNativeStackNavigator} from '@react-navigation/native-stack';
import HomeScreen from '../screens/HomeScreen';
import WebviewScreen from '../screens/WebviewScreen';
import {StaticParamList} from '@react-navigation/native';
import OpenLinkScreen from '../screens/OpenLinkScreen';

const RootStack = createNativeStackNavigator({
  initialRouteName: 'Home',
  screens: {
    Home: HomeScreen,
    OpenLink: OpenLinkScreen,
    WebviewScreen: {
      screen: WebviewScreen,
      options: {
        headerShown: false,
      },
    },
  },
});

type RootStackParamList = StaticParamList<typeof RootStack>;

declare global {
  namespace ReactNavigation {
    interface RootParamList extends RootStackParamList {}
  }
}

export default RootStack;
