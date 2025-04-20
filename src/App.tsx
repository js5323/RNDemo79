import {createStaticNavigation} from '@react-navigation/native';
import RootStack from './routers';

const Navigation = createStaticNavigation(RootStack);

function App() {
  return <Navigation />;
}

export default App;
