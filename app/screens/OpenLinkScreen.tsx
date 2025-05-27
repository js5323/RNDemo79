import {Button} from '@react-navigation/elements';
import React, {useState} from 'react';
import {StyleSheet, TextInput, View} from 'react-native';

export default function OpenLinkScreen() {
  const [link, setLink] = useState('');
  return (
    <View style={styles.container}>
      <TextInput
        value={link}
        onChangeText={setLink}
        placeholder="Enter a link to open"
      />
      <Button screen="WebviewScreen" params={{url: link}} disabled={!link}>
        Open
      </Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    gap: 20,
    padding: 20,
  },
});
