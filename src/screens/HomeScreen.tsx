import {Button} from '@react-navigation/elements';
import React from 'react';
import {View} from 'react-native';
import {useNavigation} from '@react-navigation/native';
// import WebView from 'react-native-webview';

type Props = {};

const pwaPocUrl =
  'https://wwwsit.aia.com.hk/content/dam/hk/iChange/poc/pwa/index.html';

export default function HomeScreen({}: Props) {
  const navigation = useNavigation();

  const handleViewPoc = () => {
    // Navigate to the WebView screen
    navigation.navigate('WebviewScreen', {
      url: pwaPocUrl,
    });
  };

  return (
    <View>
      <Button onPressIn={handleViewPoc}>View PWA POC</Button>
    </View>
  );
}
