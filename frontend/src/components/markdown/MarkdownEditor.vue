<template>
  <mavon-editor
      class="me-editor"
      ref="md"
      v-model="editor.value"
      @imgAdd="imgAdd"
      v-bind="editor">
  </mavon-editor>
</template>

<script>
import { ref, onMounted } from 'vue';
import { mavonEditor } from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';

import { upload } from '@/api/upload';

export default {
  name: 'MarkdownEditor',
  props: {
    editor: Object,
  },
  setup(props) {
    const md = ref();
    const editorValue = ref(props.editor.value);

    const imgAdd = (pos, $file) => {
      const formdata = new FormData();
      formdata.append('image', $file);

      upload(formdata).then(data => {
        if (data.success) {
          md.value.$img2Url(pos, data.data);
        } else {
          // Assuming you have a way to show messages, replace this with your message function
          alert(data.msg);
        }
      }).catch(err => {
        // Assuming you have a way to show messages, replace this with your message function
        alert(err);
      });
    };

    onMounted(() => {
      console.log("below is md.value!!!")
      console.log(md)
      props.editor.ref = md.value;
    });

    return {
      md,
      editorValue,
      imgAdd
    };
  },
  components: {
    mavonEditor
  }
};
</script>

<style>
.me-editor {
  z-index: 6 !important;
}

.v-note-wrapper.fullscreen {
  top: 60px !important;
}
</style>
