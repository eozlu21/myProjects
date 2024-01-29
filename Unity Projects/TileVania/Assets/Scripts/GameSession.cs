using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.Serialization;

public class GameSession : MonoBehaviour
{
    private static GameSession _gameSession;
    [SerializeField] private int playerLives = 3;
    private int score;
    [SerializeField] private TextMeshProUGUI scoreText;
    [SerializeField] private TextMeshProUGUI remainingLivesText;
    [SerializeField] private float waitAfterDeath = 2f;
    [SerializeField] private string livesUIPrefix = ": ";
    [SerializeField] private string scoreUIPrefix = "Score: ";
    // Start is called before the first frame update
    void Awake()
    {
        if (_gameSession)
        {
            Destroy(gameObject);
        }
        else
        {
            _gameSession = this;
            DontDestroyOnLoad(gameObject);
        }
    }

    private void Start()
    {
        remainingLivesText.text = livesUIPrefix + playerLives;
        scoreText.text = scoreUIPrefix + "0";
    }

    public void ProcessPlayerDeath()
    {
        if (playerLives > 1)
        {
            TakeLife();
        }
        else
        {
            ResetGameSession();
        }
        
    }

    private void ResetGameSession()
    {
        ScenePersist.Reset();
        SceneManager.LoadScene(0);
        Destroy(gameObject);
    }

    private void TakeLife()
    {
        playerLives--;
        remainingLivesText.text = livesUIPrefix + playerLives.ToString();
        StartCoroutine(ResetCurrentLevel());
    }

    IEnumerator ResetCurrentLevel()
    {
        yield return new WaitForSecondsRealtime(waitAfterDeath);
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }

    public void IncrementScore(int value)
    {
        score += value;
        scoreText.text = scoreUIPrefix + score;
    }
    // Update is called once per frame
    void Update()
    {
    }
}
